package utils;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import robot.PID;

import java.security.InvalidParameterException;

/**
 * Created by Artur on 26-Oct-16.
 */
public final class Utility {

  private static EV3LargeRegulatedMotor rMotor;
  private static EV3LargeRegulatedMotor lMotor;
  private static EV3ColorSensor lSensor;

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
    Port port = LocalEV3.get().getPort("S1");
    rMotor = new EV3LargeRegulatedMotor(MotorPort.A);
    lMotor = new EV3LargeRegulatedMotor(MotorPort.B);
    lSensor = new EV3ColorSensor(port);

    //sets the motors and sensors for the other classes

    PID.lMotor = lMotor;
    PID.rMotor = rMotor;
    PID.lSensor = lSensor;
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
