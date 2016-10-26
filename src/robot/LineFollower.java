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


    public static void start(){


        Port port = LocalEV3.get().getPort("S1");
        RegulatedMotor rMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        RegulatedMotor lMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        SensorModes colourSensor = new EV3ColorSensor(port);
        SampleProvider colourSampleProvider = colourSensor.getMode("Red");


        float[] sample = new float[colourSampleProvider.sampleSize()];

        int lval;
        int rval;
        final int dval = 200;
        double k;
        double s;

        while(true){
            colourSampleProvider.fetchSample(sample, 0);
            lval=dval;
            rval=dval;
            s=1;

            k = sample[0];
            if(k<0.12 || k>0.2){
                k = k-0.15;
                if(k<0){
                    lval = (int)(dval*(k*1.7*(-10)+1));
                    rval = (int)(dval*(s-k));
                }
                else{
                    rval = (int)(dval*(k*(10)+1));
                    lval = (int)(dval*(s-k));
                }
            }

            lMotor.setSpeed(lval);
            rMotor.setSpeed(rval);
            lMotor.forward();
            rMotor.forward();

            //display(sample[0], lval, rval);
        }
    }
}


/*

        Port port = LocalEV3.get().getPort("S1");
        RegulatedMotor rMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        RegulatedMotor lMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        SensorModes colourSensor = new EV3ColorSensor(port);
        SampleProvider colourSampleProvider = colourSensor.getMode("Red");


        float[] sample = new float[colourSampleProvider.sampleSize()];

        int lval;
        int rval;

        while(true){
            colourSampleProvider.fetchSample(sample, 0);
            lval=1;
            rval=1;

            rMotor.setSpeed(200);
            lMotor.setSpeed(200);

            float k = 1;

            if(sample[0]>0.06){
                k = sample[0];
                if(k<0.15){
                    lval=Math.round(200*k);
                    lMotor.setSpeed(lval);
                }
                else{
                    rval = Math.round(200*k);
                    rMotor.setSpeed(rval);
                }
            }

            //lMotor.forward();
            //rMotor.forward();

            display(sample[0], lval, rval);
            Delay.msDelay(1000);
        }
    }
 */