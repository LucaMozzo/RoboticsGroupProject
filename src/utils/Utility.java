package utils;

import lejos.hardware.lcd.LCD;

/**
 * Created by Artur on 26-Oct-16.
 */
public final class Utility {
  
  public static void display(String str){
    LCD.clear();
    LCD.drawString(str,1,1);
    LCD.refresh();
  }

  public static void display(float f){
    display(String.valueOf(f));
  }
}
