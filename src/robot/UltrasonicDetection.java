package robot;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.UARTSensor;
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.Device;
import lejos.utility.Delay;
import utils.Utility;

/**
 * Created by Artur on 26-Oct-16.
 */
public final class UltrasonicDetection {

    public static EV3MediumRegulatedMotor sMotor;
    public static EV3UltrasonicSensor sSensor;

    public static void start(Thread lineFollowerThread, Thread avoidObstacleThread) throws InterruptedException {
        //DONT TOUCH-----------------------------------------------------------------
        MultiThreadingSync.setLineFollowerMode(); //turns off avoid obstacle mode
        lineFollowerThread.start();
        avoidObstacleThread.start();
        //-------------------------------------------------------------------------
        Utility.display("starting threads");
        float[] sampleSonar = new float[sSensor.sampleSize()];
        while(true) {
            while (MultiThreadingSync.getMode() == 1) {
                sSensor.fetchSample(sampleSonar, 0);
                if (sampleSonar[0] < 0.1) {
                    //Utility.display("avoid obstacle");
                    //MultiThreadingSync.setAvoidObstacleMode();
                }
            }
        }

    }

}
