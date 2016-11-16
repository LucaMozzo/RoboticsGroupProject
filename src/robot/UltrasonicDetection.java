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
public class UltrasonicDetection implements Runnable {
    public void run() {

        RegulatedMotor motor = new EV3MediumRegulatedMotor(MotorPort.C);
        motor.setSpeed(50);
        //might need to adjust the speed of the medium motor... but based on the circular motion factor
        EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
       // SampleProvider distanceSampleProvider = ultrasonicSensor.getDistanceMode();
        //  SampleProvider disatanceSampleProvider= ultrasonicSensor.getDistanceMode();   should work but doesn't
                                    //gives me a sample provider for the distanceMode
        float[] sample = new float[ultrasonicSensor.getDistanceMode().sampleSize()];
        //while its 10 or meter has to be checked far from the obstacle + 2 cm for the deceleration
        ultrasonicSensor.enable();
        while (ultrasonicSensor.isEnabled()) {
            while (sample[0] <= 0.12) { //gotta take a sample size today first
                //turn the chassis by 90 degrees?
                //gyroscope to keep the head of the sensor straight from the beginning or?

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
