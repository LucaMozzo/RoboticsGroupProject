package robot;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import utils.Utility;

/**
 * Created by asif5 on 28/10/2016.
 */
public class FetchSamples {

    public static void start(){
        Port port = LocalEV3.get().getPort("S1");
        SensorModes colourSensor = new EV3ColorSensor(port);
        SampleProvider colourSampleProvider = colourSensor.getMode("Red");


        float[] sample = new float[colourSampleProvider.sampleSize()];

        while(true){
            colourSampleProvider.fetchSample(sample,0);
            Utility.display(String.valueOf(sample[0]));
            Delay.msDelay(100);
        }
    }
}
