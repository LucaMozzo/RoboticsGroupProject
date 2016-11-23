package robot;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import utils.Utility;

/**
 * Created by asif5 on 28/10/2016.
 */
public class FetchSamples {

    public static EV3MediumRegulatedMotor sMotor;
    public static EV3LargeRegulatedMotor rMotor;
    public static EV3LargeRegulatedMotor lMotor;
    public static EV3ColorSensor lSensor;
    public static EV3UltrasonicSensor sSensor;

    public static void start(){

        while(true){
            float[] sampleSonar = new float[sSensor.sampleSize()];
            sSensor.fetchSample(sampleSonar,0);


            float[] sampleLight = new float[lSensor.sampleSize()];

            while(sampleSonar[0] >0.1){
                sSensor.fetchSample(sampleSonar,0);
                rMotor.setSpeed(300);
                lMotor.setSpeed(300);
                lMotor.forward();
                rMotor.forward();
            }

            rMotor.stop();
            lMotor.stop();

            sMotor.rotate(-90, true); // true means that code will not wait for return to continue
            rMotor.rotate(-210, true);
            lMotor.rotate(210, true);
            lSensor.fetchSample(sampleLight,0);

            //PID VALUES
            int lval;
            int rval;
            int dval = 50; // base motor value
            float k = 293; //constant of proportionality
            float e; // error term and sensor recorded value (dual use
            float kSym = 1.3f;
            int Kd = 40; // deferential constant
            float lastError = 0; //
            float Ki = 4.4f; //integral constant
            int integral = 0;

            while(sampleLight[0] < 0.45){
                lSensor.fetchSample(sampleLight,0);
                sSensor.fetchSample(sampleSonar, 0);

                lval = dval;
                rval = dval;
                e = sampleLight[0];//offset
                if (e < 0.06 || e > 0.15) { // filtering out  noise, so that robot can go straight
                    e -= 0.1;
                    lastError = e - lastError;
                    integral += e;
                    rval = (int) (dval + (k * kSym * e) + Kd * lastError + Ki * integral); //sensor reading are no symetrical, hence constant 1.7 adjust
                    lval = (int) (dval - ((k * e) + Kd * lastError + Ki * integral));

                    lastError = e;
                }
                lMotor.setSpeed(lval);
                rMotor.setSpeed(rval);
                lMotor.forward();
                rMotor.forward();
            }

            Delay.msDelay(5000);
        }

    }
}
