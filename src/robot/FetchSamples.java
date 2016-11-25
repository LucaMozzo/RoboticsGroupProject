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

            while(sampleSonar[0] >0.05){
                sSensor.fetchSample(sampleSonar,0);
                rMotor.setSpeed(300);
                lMotor.setSpeed(300);
                lMotor.forward();
                rMotor.forward();
                utils.Utility.display(new String[]{"Error"}, new float[]{sampleSonar[0]});
            }

            rMotor.stop();
            lMotor.stop();

            sMotor.rotate(-90, true);
            rMotor.rotate(-155, true);
            lMotor.rotate(155, true);
            Delay.msDelay(1000);
            sSensor.fetchSample(sampleSonar, 0);

            /*
            while(sampleSonar[0] > 0.03){
                sSensor.fetchSample(sampleSonar, 0);
                lMotor.rotate(10);
                rMotor.rotate(-10);
            }

            rMotor.stop();
            lMotor.stop();
            Delay.msDelay(5000);




            while(lastError>0){
                sSensor.fetchSample(sampleSonar, 0);
                e = sampleSonar[0];//offset
                lval = (int) (100+Kd*lastError);
                rval = (int) (100-Kd*lastError);
                lMotor.setSpeed(lval);
                rMotor.setSpeed(rval);
                lastError = e;
                rMotor.forward();
                lMotor.forward();
                utils.Utility.display(new String[]{"Last Error", "Error", "lval", "rval"}, new float[]{lastError, e, (float) lval, (float) rval});

            }
            */
            //PD VALUES
            int lval = 0;
            int rval = 0;
            int dval = 200; // base motor value

            float e; // error term and sensor recorded value (dual use
            int Kd = 300; // deferential constant
            float lastError = 0; //
            float k = 500; //constant of proportionality
            float kSym = 1.3f;
            sSensor.fetchSample(sampleSonar, 0);
            Delay.msDelay(1000);

            while(true){
                //lSensor.fetchSample(sampleLight,0);
                sSensor.fetchSample(sampleSonar, 0);
                e = sampleSonar[0];//offset
                if (e < 0.03 || e > 0.05) { // filtering out  noise, so that robot can go straight
                    e -= 0.04;
                    lastError = e - lastError;
                    rval = (int) (dval + (k * kSym * e) + Kd * lastError ); //sensor reading are no symetrical, hence constant 1.7 adjust
                    lval = (int) (dval - (k * e) + Kd * lastError );
                    lastError = e;
                }
                lMotor.setSpeed(lval);
                rMotor.setSpeed(rval);
                lMotor.forward();
                rMotor.forward();

                float[] vals = {rval, dval, e, sampleSonar[0]};
                String[] str = {"rval: ", "lval: ", "error", "sonar" };
                utils.Utility.display(str, vals);
            }
        }

    }
}
