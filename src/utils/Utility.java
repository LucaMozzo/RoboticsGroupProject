package utils;

import lejos.hardware.lcd.LCD;

import java.security.InvalidParameterException;

/**
 * Created by Artur on 26-Oct-16.
 */
public final class Utility {
  
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
