package robot;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

/**
 * Created by Artur on 21-Oct-16.
 */
public class TestEncoder {

    public static void start(){
        RegulatedMotor rMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        RegulatedMotor lMotor = new EV3LargeRegulatedMotor(MotorPort.B);

        /*rMotor.setSpeed(200);
        lMotor.setSpeed(200);
        rMotor.forward();
        lMotor.forward();*/

        while(true){
            int left = lMotor.getTachoCount();
            int right = rMotor.getTachoCount();
            display(left,right);
            Delay.msDelay(100);
        }
    }

    public static void display(int left, int right){
        LCD.clear();
        LCD.drawString("Left: " + String.valueOf(left), 0 , 1);
        LCD.drawString("Right: " + String.valueOf(right), 0 , 2);
    }
}
