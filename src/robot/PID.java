package robot;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import utils.Utility;

/**
 * Created by lucam on 16/11/2016.
 */
public class PID extends Thread{

    public static EV3LargeRegulatedMotor rMotor;
    public static EV3LargeRegulatedMotor lMotor;
    public static EV3ColorSensor lSensor;

    public void run() {
        SampleProvider colourSampleProvider = lSensor.getMode("Red");

        float[] sample = new float[colourSampleProvider.sampleSize()];

        int lval;
        int rval;
        int dval = 225; // base motor value
        float k = 290; //constant of proportionality
        float e; // error term and sensor recorded value (dual use

        float kSym = 1.3f;
        int index = 0; //menu index

        int Kd = 236; // deferential constant
        float lastError = 0; //

        float Ki = 12.2f; //integral constant
        int integral = 0;

        while (true) {
            while (MultiThreadingSync.getMode() == 1/*Button.getButtons() == 0*/) {
                colourSampleProvider.fetchSample(sample, 0);
                lval = dval;
                rval = dval;
                e = sample[0];//offset
                if (e < 0.3 || e > 0.45) { // filtering out  noise, so that robot can go straight
                    e -= 0.42;
                    lastError = e - lastError; //TODO simplify
                    e += e;
                    rval = (int) (dval + (k * kSym * e) + Kd * lastError + Ki * integral); //sensor reading are no symetrical, hence constant 1.7 adjust
                    lval = (int) (dval - ((k * e) + Kd * lastError + Ki * integral));
                    lastError = e;
                }
                lMotor.setSpeed(lval);
                rMotor.setSpeed(rval);
                lMotor.forward();
                rMotor.forward();
            }


            lMotor.setSpeed(0);
            rMotor.setSpeed(0);
            lMotor.forward();
            rMotor.forward();
            while(MultiThreadingSync.getMode() == 2){ Delay.msDelay(100);}
            Utility.display("PID");
        }
    }
}
