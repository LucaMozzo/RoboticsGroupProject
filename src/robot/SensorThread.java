package robot;


import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import utils.Utility;

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
                sonarSampleProvider.fetchSample(sonarSample, 0);
                Utility.display(sonarSample[0]);
                if(sonarSample[0]<0.10){
                    if(obstacleDetected) { //curtain detected
                        MultiThreadingSync.exit();
                      break;
                    }
                    else {
                        MultiThreadingSync.setAvoidObstacleMode();
                        Delay.msDelay(2000);
                    }
                }
            }
            else if(MultiThreadingSync.getMode() == 2){
                lightSampleProvider.fetchSample(lightSample, 0);
                if(lightSample[0]<0.45){
                    obstacleDetected = true;
                    MultiThreadingSync.setFindLineMode();
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
