package robot;


import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import utils.Utility;

import javax.rmi.CORBA.Util;

/**
 * Created by Artur on 02-Dec-16.
 */
public class SensorThread extends Thread {
    //------------Sensor Values  Provider---------
    public static SampleProvider sonarSampleProvider;
    public static SampleProvider lightSampleProvider;
    //------------Sensor Values-------------------
    public static float[] sonarSample;
    public static float[] lightSample;

    public static boolean obstacleDetected = false;

    public void run(){
        while(true){
            if(MultiThreadingSync.getMode() == 1){
                boolean turn = false; //never used?
                float distance = 0.15f;
                MultiThreadingSync.detectedDistance = distance;
                sonarSampleProvider.fetchSample(sonarSample, 0);
                Utility.sMotor.setSpeed(200);
                //Utility.sMotor.setAcceleration(5000);
                scanning:
                while (sonarSample[0] > distance) {
                    Utility.sMotor.forward();
                    while (Utility.sMotor.getTachoCount() < 30) {
                        sonarSampleProvider.fetchSample(sonarSample, 0);
                        if (sonarSample[0] < distance) {
                            Utility.sMotor.stop();
                            MultiThreadingSync.angle = Utility.sMotor.getTachoCount();
                            MultiThreadingSync.detectedDistance = sonarSample[0];
                            break scanning;
                        }
                    }
                    Utility.sMotor.backward();
                    while (Utility.sMotor.getTachoCount() > -30) {
                        sonarSampleProvider.fetchSample(sonarSample, 0);
                        if (sonarSample[0] < distance) {
                            Utility.sMotor.stop();
                            MultiThreadingSync.angle = Utility.sMotor.getTachoCount();
                            MultiThreadingSync.detectedDistance = sonarSample[0];
                            break scanning;
                        }
                    }
                    sonarSampleProvider.fetchSample(sonarSample, 0);
                }


                MultiThreadingSync.setAvoidObstacleMode();

                Delay.msDelay(8000);
            }
            else if(MultiThreadingSync.getMode() == 2){
                lightSampleProvider.fetchSample(lightSample, 0);
                if(lightSample[0]<0.45){
                    obstacleDetected = true;
                    MultiThreadingSync.setFoundLineMode();
                }
            }

            else if(MultiThreadingSync.getMode() == 3){
                lightSampleProvider.fetchSample(lightSample, 0);
                if(lightSample[0]<0.1){
                    MultiThreadingSync.setLineFollowerMode();
                }
            }
        }
    }
}
