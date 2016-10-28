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

/**
 * @author luca
 * Class provides methods for following the line
 */
public final class LineFollower {

    public static void display(float sensor, int lval, int rval){
        LCD.clearDisplay();
        LCD.drawString("sensor value  " + String.valueOf(sensor), 0, 0);
        LCD.drawString("left value" + String.valueOf(lval), 0, 2);
        LCD.drawString("right value" +String.valueOf(rval), 0, 3);

    }


    public static void start() {


        Port port = LocalEV3.get().getPort("S1");
        RegulatedMotor rMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        RegulatedMotor lMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        SensorModes colourSensor = new EV3ColorSensor(port);
        SampleProvider colourSampleProvider = colourSensor.getMode("Red");


        float[] sample = new float[colourSampleProvider.sampleSize()];

        int lval;
        int rval;
        final int dval = 200; // base motor value
        double k = 5; //constant of proportionality
        double e; // error term and sensor recorded value (dual use

        while(true){
            colourSampleProvider.fetchSample(sample, 0);
            lval=dval;
            rval=dval;

            e = sample[0];
            if(e<0.30 || e>0.42){ // filtering out  noise, so that robot can go straight
                e = e-0.35;
                if(e<0) { // two lines below are the P part of the PID controller
                    lval = (int) (dval - (k * 1.7 * e)); //sensor reading are no symetrical, hencea constant 1.7 adjust
                    rval = (int) (dval + (k*e));
                }
            }

            lMotor.setSpeed(lval);
            rMotor.setSpeed(rval);
            lMotor.forward();
            rMotor.forward();

        }
    }
}
