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

/**
 * Created by Artur on 26-Oct-16.
 */
public class UltrasonicDetection implements Runnable {
    public void run() {
        RegulatedMotor motor = new EV3MediumRegulatedMotor(MotorPort.C);
        motor.setSpeed(50);
        SensorModes ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distanceSampleProvider = ultrasonicSensor.getMode("Distance");


        float[] sample = new float[distanceSampleProvider.sampleSize()];

        while(true){
            motor.rotate(45);
            distanceSampleProvider.fetchSample(sample,0);
            motor.rotate(45);
            //take a sample
            motor.rotate(45);
            //take a sample
            motor.rotate(45);
            //take a sample
            Delay.msDelay(100);
            motor.rotate(-180);
        }
    }


}
