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
import lejos.utility.Delay;
import utils.Utility;

/**
 * Created by Artur on 26-Oct-16.
 */
public class UltrasonicDetection implements Runnable {
    public void run() {

        RegulatedMotor motor = new EV3MediumRegulatedMotor(MotorPort.C);
        motor.setSpeed(50);
        SensorModes ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distanceSampleProvider = ultrasonicSensor.getMode("Distance");
      //  SampleProvider disatanceSampleProvider= ultrasonicSensor.getDistanceMode();   should work but doesn't

        float[] sample = new float[distanceSampleProvider.sampleSize()];
        //while its 10 cm far from the obstacle + 2 cm for the deceleration
 while (sample[0]<=0.12) {

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
