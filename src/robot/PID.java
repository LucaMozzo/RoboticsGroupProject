package robot;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * Created by lucam on 16/11/2016.
 */
public class PID {

    public static EV3LargeRegulatedMotor rMotor;
    public static EV3LargeRegulatedMotor lMotor;
    public static EV3ColorSensor lSensor;

    public static void start() {
        SampleProvider colourSampleProvider = lSensor.getMode("Red");


        float[] sample = new float[colourSampleProvider.sampleSize()];

        int lval;
        int rval;
        int dval = 200; // base motor value
        float k = 320; //constant of proportionality
        float e; // error term and sensor recorded value (dual use

        float kSym = 1.3f;
        int index = 0; //menu index

        int Kd = 100; // deferential constant
        float lastError = 0; //

        float Ki = 1; //integral constant
        int integral = 0;

        while (true) {
            while (Button.getButtons() == 0) {
                colourSampleProvider.fetchSample(sample, 0);

                lval = dval;
                rval = dval;
                e = sample[0];//offset
                if (e < 0.3 || e > 0.45) { // filtering out  noise, so that robot can go straight
                    e -= 0.42;
                    float derivative = e - lastError; //TODO simplify
                    integral += e;
                    rval = (int) (dval + (k * kSym * e) + Kd * derivative + Ki * integral); //sensor reading are no symetrical, hence constant 1.7 adjust
                    lval = (int) (dval - ((k * e) + Kd * derivative + Ki * integral));

                    lastError = e;
                }
                lMotor.setSpeed(lval);
                rMotor.setSpeed(rval);
                lMotor.forward();
                rMotor.forward();
                //Delay.msDelay(500);
            }

//######################Tuning Buttons##################################################################################

            if (Button.getButtons() == Button.ID_UP) {
                if (index > 0)
                    --index;
            }
            else if (Button.getButtons() == Button.ID_DOWN) {
                ++index;
            }
            else if (Button.getButtons() == Button.ID_RIGHT){
                if(index%5 == 0)
                    //the values might need to be modified from inside the array with indexes since the array doesn't update idk why
                    kSym += 0.05;//0.1
                else if(index%5 == 1)
                    k += 1;//10
                else if(index%5 == 2)
                    Kd += 1;//10
                else if(index%5 == 4)
                    dval += 5;//20
                else if(index%5 == 3)
                    Ki +=0.2;//1
            }
            else if (Button.getButtons() == Button.ID_LEFT){
                if(index%5 == 0)
                    kSym -= 0.05;//0.1
                else if(index%5 == 1)
                    k -= 1;//10
                else if(index%5 == 2)
                    Kd -= 1;//10
                else if(index%5 == 4)
                    dval -= 5;//20
                else if(index%5 == 3)
                    Ki -=0.2;//1
            }
            else if(Button.getButtons() == Button.ID_ENTER) { //PAUSE
                Delay.msDelay(500);
                lMotor.setSpeed(0);
                rMotor.setSpeed(0);
                lMotor.forward();
                rMotor.forward();
                while (Button.getButtons() != Button.ID_ENTER){
                    Delay.msDelay(200);
                }
            }

            float[] vals = {kSym, k, Kd, Ki, dval};

            String[] str = {"Ksym: ", "Kp: ", "Kd: ", "Ki: ", "def speed: "};
            str[index%str.length]= '>' + str[index%str.length];
            utils.Utility.display(str, vals);

            Delay.msDelay(100);
        }
    }
}
