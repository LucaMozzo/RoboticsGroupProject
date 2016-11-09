package robot;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import utils.Utility;

/**
 * Created by Artur on 09-Nov-16.
 */

public final class TestLight {

    public static void start(){
        SensorModes lSensor = new EV3ColorSensor(SensorPort.S1);
        SampleProvider colourSampleProvider = lSensor.getMode("RED");

        float[] sample = new float[colourSampleProvider.sampleSize()];

        while(true) {
            colourSampleProvider.fetchSample(sample, 0);
            Utility.display(sample[0]);
            Delay.msDelay(100);
        }
    }


}
