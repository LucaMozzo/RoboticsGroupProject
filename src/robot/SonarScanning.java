package robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import utils.Utility;

/**
 * Created by Artur on 02-Dec-16.
 */
public class SonarScanning extends Thread {

    //------------Motors--------------------------
    public static EV3MediumRegulatedMotor sMotor;
    public static EV3LargeRegulatedMotor rMotor;
    public static EV3LargeRegulatedMotor lMotor;
    //------------Sensor Values  Provider---------
    public static SampleProvider sonarSampleProvider;
    public static SampleProvider lightSampleProvider;
    //------------Sensor Values-------------------
    public static float[] sonarSample;
    public static float[] lightSample;

    public void run(){

        boolean turn = false;
        float distance = 0.15f;
        float detectedDestance = distance;
        while(MultiThreadingSync.getMode()==1){
            sonarSampleProvider.fetchSample(sonarSample, 0);
            lMotor.setSpeed(100);
            scanning:
            while(sonarSample[0]>distance){
                sMotor.forward();
                while(sMotor.getTachoCount()<90){
                    sonarSampleProvider.fetchSample(sonarSample, 0);
                    if(sonarSample[0] <distance)break scanning;
                }
                sMotor.backward();
                while(sMotor.getTachoCount()>-90){
                    sonarSampleProvider.fetchSample(sonarSample, 0);
                    if(sonarSample[0] <distance)break scanning;
                }
                sonarSampleProvider.fetchSample(sonarSample, 0);
            }
            sMotor.setSpeed(100);
            sMotor.backward();
            while(sMotor.getTachoCount()>-90){
                Delay.msDelay(10);
            }
            sMotor.stop();
            lMotor.stop();
            rMotor.stop();
            rMotor.setSpeed(100);
            lMotor.setSpeed(100);
            lMotor.forward();
            rMotor.backward();
            sonarSampleProvider.fetchSample(sonarSample, 0);
            while(sonarSample[0] > detectedDestance+0.05){
                sonarSampleProvider.fetchSample(sonarSample, 0);
            }
            Delay.msDelay(100);
            lMotor.stop();
            rMotor.stop();
            Delay.msDelay(1000);
            //Utility.rotate(90+sMotor.getTachoCount());
            //MultiThreadingSync.setAvoidObstacleMode();
        }
    }
}
