package robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.robotics.SampleProvider;
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

        while(MultiThreadingSync.getMode()==1){
            sonarSampleProvider.fetchSample(sonarSample, 0);
            while(sonarSample[0]>0.15){
                if(sMotor.getTachoCount()>90){
                    turn = false;
                    sMotor.rotate(-5);
                }
                else if(sMotor.getTachoCount()<-90){
                    turn = true;
                    sMotor.rotate(5);
                }

                else if(turn){
                    sMotor.rotate(5);
                }

                else sMotor.rotate(-5);
                sonarSampleProvider.fetchSample(sonarSample, 0);
            }
            sMotor.rotateTo(-90);
            rMotor.rotate((int) (-2.16*90-sMotor.getTachoCount()), true);
            lMotor.rotate((int)(2.16*90-sMotor.getTachoCount()));
            //Utility.rotate(90+sMotor.getTachoCount());
            //MultiThreadingSync.setAvoidObstacleMode();
        }
    }
}
