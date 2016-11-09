package robot;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import utils.Utility;

/**
 * @author luca
 * Class provides methods for following the line
 */
public final class LineFollower {

    public static void start() {


        Port port = LocalEV3.get().getPort("S1");
        RegulatedMotor rMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        RegulatedMotor lMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        SensorModes lSensor = new EV3ColorSensor(port);
        SampleProvider colourSampleProvider = lSensor.getMode("Red");


        float[] sample = new float[colourSampleProvider.sampleSize()];

        int lval;
        int rval;
        final int dval = 200; // base motor value
        double k = 500; //constant of proportionality
        double e; // error term and sensor recorded value (dual use

        while(true){
            colourSampleProvider.fetchSample(sample, 0);

            lval=dval;
            rval=dval;
            e = sample[0];
            if(e<0.3 || e>0.45){ // filtering out  noise, so that robot can go straight
                e = e-0.40;
                lval = (int) (dval - (k*e)); //sensor reading are no symetrical, hence constant 1.7 adjust
                rval = (int) (dval + (k*2*e));

                /*String[] strings = {"lval: ", "rval: ", "sensor: "};
                float[] floats = {lval, rval, sample[0]};
                Utility.display(strings, floats);*/
            }
            lMotor.setSpeed(lval);
            rMotor.setSpeed(rval);
            lMotor.forward();
            rMotor.forward();
            //Delay.msDelay(500);

        }
    }
}
