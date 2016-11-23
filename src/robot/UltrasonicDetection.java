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

    public static void start(Thread lineFollowerThread/*, Thread avoidObstacleThread*/) throws InterruptedException {

        RegulatedMotor motor = new EV3MediumRegulatedMotor(MotorPort.C);
        motor.setSpeed(50);
        //might need to adjust the speed of the medium motor... but based on the circular motion factor
        EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distanceSampleProvider = ultrasonicSensor.getMode("Distance");
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
            motor.rotate(-180);
        }
    }

}
