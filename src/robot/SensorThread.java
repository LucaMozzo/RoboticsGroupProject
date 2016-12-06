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
        boolean turn = false;
        while(true){
            if(MultiThreadingSync.getMode() == 1){
                /*sonarSampleProvider.fetchSample(sonarSample, 0);
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
                }*/
                float distance = 0.15f;
                MultiThreadingSync.detectedDestance = distance;

                sonarSampleProvider.fetchSample(sonarSample, 0);
                Utility.lMotor.setSpeed(100);
                scanning:
                while(sonarSample[0]>distance){
                    Utility.sMotor.forward();
                    while(Utility.sMotor.getTachoCount()<90){
                        sonarSampleProvider.fetchSample(sonarSample, 0);
                        if(sonarSample[0] <distance)break scanning;
                    }
                    Utility.sMotor.backward();
                    while(Utility.sMotor.getTachoCount()>-90){
                        sonarSampleProvider.fetchSample(sonarSample, 0);
                        if(sonarSample[0] <distance)break scanning;
                    }
                    sonarSampleProvider.fetchSample(sonarSample, 0);
                }

                MultiThreadingSync.setAvoidObstacleMode();
                Delay.msDelay(3000);

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
