package utils;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import robot.Control;
import robot.PIDTuner;
import robot.SensorThread;
import robot.SonarScanning;

import java.security.InvalidParameterException;

/**
 * Created by Artur on 26-Oct-16.
 */
public final class Utility {

    public static EV3LargeRegulatedMotor rMotor;
    public static EV3LargeRegulatedMotor lMotor;
    public static EV3ColorSensor lSensor;
    public static EV3MediumRegulatedMotor sMotor;
    public static EV3UltrasonicSensor sSensor;
    public static SampleProvider lightSampleProvider;
    public static float[] lightSample;
    public static SampleProvider sonarSampleProvider;
    public static float[] sonarSample;

    public static void setup(){
        Port port1 = LocalEV3.get().getPort("S1");
        Port port2 = LocalEV3.get().getPort("S2");
        rMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        lMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        sMotor = new EV3MediumRegulatedMotor(MotorPort.C);

        //----------Setting up Light Sensor-------------------------------------------
        lSensor = new EV3ColorSensor(port1);
        lightSampleProvider = lSensor.getMode("Red");
        lightSample = new float[lightSampleProvider.sampleSize()];

        //----------Setting up Sonar Sensor-------------------------------------------
        sSensor = new EV3UltrasonicSensor(port2);
        sonarSampleProvider = sSensor.getMode("Distance");
        sonarSample = new float[sonarSampleProvider.sampleSize()];

        //----------Setting up motors in other classes ---------------------------------
        Control.lMotor = lMotor;
        Control.rMotor = rMotor;
        Control.sMotor = sMotor;

        Control.lightSample = lightSample;
        Control.lightSampleProvider = lightSampleProvider;
        Control.sonarSample = sonarSample;
        Control.sonarSampleProvider = sonarSampleProvider;


        SensorThread.lightSample = lightSample;
        SensorThread.lightSampleProvider = lightSampleProvider;
        SensorThread.sonarSample = sonarSample;
        SensorThread.sonarSampleProvider = sonarSampleProvider;

        PIDTuner.lMotor = lMotor;
        PIDTuner.rMotor = rMotor;
        PIDTuner.lSensor = lSensor;

        SonarScanning.lMotor = lMotor;
        SonarScanning.rMotor = rMotor;
        SonarScanning.sMotor = sMotor;

        SonarScanning.lightSample = lightSample;
        SonarScanning.lightSampleProvider = lightSampleProvider;
        SonarScanning.sonarSample = sonarSample;
        SonarScanning.sonarSampleProvider = sonarSampleProvider;



    }

    public static void display(String[] str, float[] f){
        if(str.length == f.length){
            LCD.clear();
            for(int i = 0; i < f.length; ++i){
                //display(str[i] + String.valueOf(f[i]), i+1);
                LCD.drawString(str[i] + String.valueOf(f[i]),1, i+1);
                LCD.refresh();
            }
        }
        else
            throw new InvalidParameterException("String array and float array must have the same size");
    }

    public static void display(String str){
        LCD.clear();
        LCD.drawString(str,1,1);
        LCD.refresh();
    }

    public static void display(String str, int yPos){
        LCD.clear();
        LCD.drawString(str,1,yPos);
        LCD.refresh();
    }

    public static void display(float f){
        display(String.valueOf(f));
    }

    public static void display(String str, float f){
        display(str + String.valueOf(f));
    }

    public static void rotate(double angle){//radians cus we ain't plebs
        rMotor.rotate((int) (2.16*angle), true);
        lMotor.rotate((int)(-2.16*angle));
    }

    public static void encoderMedium(){
        display(sMotor.getTachoCount());
    }


}
