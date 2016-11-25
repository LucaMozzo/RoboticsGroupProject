package utils;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import robot.Avoid;
import robot.FetchSamples;
import robot.PID;
import robot.UltrasonicDetection;

import java.security.InvalidParameterException;

/**
 * Created by Artur on 26-Oct-16.
 */
public final class Utility {

    private static EV3LargeRegulatedMotor rMotor;
    private static EV3LargeRegulatedMotor lMotor;
    private static EV3ColorSensor lSensor;
    private static EV3MediumRegulatedMotor sMotor;
    private static EV3UltrasonicSensor sSensor;

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

    public static void setup(){
        Port port1 = LocalEV3.get().getPort("S1");
        Port port2 = LocalEV3.get().getPort("S2");
        rMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        lMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        sMotor = new EV3MediumRegulatedMotor(MotorPort.C);
        lSensor = new EV3ColorSensor(port1);
        sSensor = new EV3UltrasonicSensor(port2);

        //sets the motors and sensors for the other classes

        PID.lMotor = lMotor;
        PID.rMotor = rMotor;
        PID.lSensor = lSensor;
        UltrasonicDetection.sMotor = sMotor;
        UltrasonicDetection.sSensor = sSensor;

        Avoid.lMotor = lMotor;
        Avoid.rMotor = rMotor;
        Avoid.sSensor = sSensor;
        Avoid.sMotor = sMotor;
        Avoid.lSensor = lSensor;

        FetchSamples.lMotor = lMotor;
        FetchSamples.rMotor = rMotor;
        FetchSamples.lSensor = lSensor;
        FetchSamples.sMotor = sMotor;
        FetchSamples.sSensor = sSensor;
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
}
