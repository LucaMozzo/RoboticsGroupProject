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
    public static EV3LargeRegulatedMotor rMotor;
    public static EV3LargeRegulatedMotor lMotor;
    public static EV3ColorSensor lSensor;
    public static EV3UltrasonicSensor uSonar;

    public static void start(Thread lineFollowerThread/*, Thread avoidObstacleThread*/) throws InterruptedException {

        sMotor.setSpeed(50);
        //might need to adjust the speed of the medium motor... but based on the circular motion factor
        SampleProvider distanceSampleProvider = uSonar.getMode("Distance");
        float[] sample = new float[distanceSampleProvider.sampleSize()];
        //while its 10 or meter has to be checked far from the obstacle + 2 cm for the deceleration

        while (true) {
            distanceSampleProvider.fetchSample(sample, 0);

            Utility.display("aaaaaaaaaa");
            if (sample[0] <= 0.1) {

                //if it's turned more than ~120 degrees, find the line and resume with the PID
            }
            else {
                //get farther
            }
/*
        for(int i = 0; i < 45; ++i) {
            motor.rotate(4);
            distanceSampleProvider.fetchSample(sample, 0);
            Utility.display(sample[0]);
            Delay.msDelay(10);
        }*/
            sMotor.rotate(-180);
        }
    }

}
