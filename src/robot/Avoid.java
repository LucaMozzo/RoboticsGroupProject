package robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.utility.Delay;

/**
 * Created by lucam on 23/11/2016.
 */
public class Avoid extends Thread {

    //private Thread ultrasonicThread;

    public static EV3MediumRegulatedMotor sMotor;
    public static EV3LargeRegulatedMotor rMotor;
    public static EV3LargeRegulatedMotor lMotor;
    public static EV3ColorSensor lSensor;
    public static EV3UltrasonicSensor sSensor;

    /*public Avoid(Thread ultrasonicThread){
        this.ultrasonicThread = ultrasonicThread;
    }*/

    @Override
    public void run(){
        float[] sampleLight = new float[lSensor.sampleSize()];
        float[] sampleSonar = new float[sSensor.sampleSize()];


        sSensor.fetchSample(sampleSonar,0);

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
        //Delay.msDelay(1000);
        sSensor.fetchSample(sampleSonar, 0);

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

        while(true){

            while(sampleLight[0] > 0.45){
                lSensor.fetchSample(sampleLight,0);
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
            MultiThreadingSync.setLineFollowerMode();
            sMotor.rotate(90, true);
            while(MultiThreadingSync.getMode() == 1) { Delay.msDelay(100); }
        }

    }
}
