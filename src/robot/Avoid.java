package robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import utils.Utility;

import javax.rmi.CORBA.Util;

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



    @Override
    public void run(){
        SampleProvider colourSampleProvider = lSensor.getMode("Red");
        float[] sampleLight = new float[colourSampleProvider.sampleSize()];
        float[] sampleSonar = new float[sSensor.sampleSize()];

        sSensor.fetchSample(sampleSonar,0);

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
        boolean flag = true;
        while(flag){

            while(MultiThreadingSync.getMode() == 2){
                rMotor.stop();
                lMotor.stop();

                sMotor.rotate(-90, true);
                rMotor.setSpeed(40);
                lMotor.setSpeed(260);
                lMotor.forward();
                rMotor.forward();
                Delay.msDelay(850);

                rMotor.stop();
                lMotor.stop();


                colourSampleProvider.fetchSample(sampleLight,0);
                Utility.display(sampleLight[0]);
                //Delay.msDelay(5000);

                while(sampleLight[0] > 0.45){
                    colourSampleProvider.fetchSample(sampleLight,0);
                    sSensor.fetchSample(sampleSonar, 0);
                    e = sampleSonar[0];//offset

                    if (e < 0.03 || e > 0.05) { // filtering out  noise, so that robot can go straight
                        e -= 0.04;
                        lastError = e - lastError;
                        rval = (int) (dval + (k * kSym * e) + Kd * lastError ); //sensor reading are no symetrical, hence constant ksym adjust
                        lval = (int) (dval - ((k * e) + Kd * lastError));
                        lastError = e;
                    }
                    lMotor.setSpeed(lval);
                    rMotor.setSpeed(rval);
                    lMotor.forward();
                    rMotor.forward();

                }
                rMotor.stop();
                lMotor.stop();
                sMotor.rotate(90, true);


                flag = false;
                break;
            }

        }
        if(MultiThreadingSync.getMode() == 2) {
            //get back to the line
            Utility.display(new String[]{"1", "val: "}, new float[]{0.0f, sampleLight[0]});
            rMotor.setSpeed(50);
            lMotor.setSpeed(50);
            rMotor.forward();
            lMotor.forward();
            while (sampleLight[0] < 0.60) {
                colourSampleProvider.fetchSample(sampleLight, 0);
            }
            rMotor.stop();
            lMotor.stop();
            //back to the black line
            Utility.display(new String[]{"2", "val: "}, new float[]{0.0f, sampleLight[0]});
            colourSampleProvider.fetchSample(sampleLight, 0);
            rMotor.setSpeed(20);
            lMotor.setSpeed(50);
            rMotor.backward();
            lMotor.forward();

            while (sampleLight[0] > 0.20) {
                colourSampleProvider.fetchSample(sampleLight, 0);
            }
            rMotor.stop();
            lMotor.stop();

            //black line but other side
            Utility.display(new String[]{"3", "val: "}, new float[]{0.0f, sampleLight[0]});
            colourSampleProvider.fetchSample(sampleLight, 0);
            while (sampleLight[0] < 0.40) {
                colourSampleProvider.fetchSample(sampleLight, 0);
            }

            MultiThreadingSync.setLineFollowerMode();
        }
    }
}
