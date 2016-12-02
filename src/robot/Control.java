package robot;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import com.sun.org.apache.xpath.internal.operations.Mult;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import utils.Utility;

/**
 * Created by Artur on 02-Dec-16.
 */
public class Control {

    //------------Motors--------------------------
    public static EV3MediumRegulatedMotor sMotor;
    public static EV3LargeRegulatedMotor rMotor;
    public static EV3LargeRegulatedMotor lMotor;
    //------------Sensor Values  Provider---------
    public static SampleProvider sonarSampleProvider;
    public static SampleProvider lightSampleProvider;
    //------------Sensor Values-------------------
    public static float[] sonarSample;
    public static float[] lightSample;


    public static void pid(){
        Utility.display("PID Luanched");
        int lval = 0;
        int rval = 0;
        int dval = 220; // base motor value
        float k = 260; //constant of proportionality
        float e; // error term and sensor recorded value (dual use

        //---------------PID Parameters------------------
        float kSym = 1.25f;
        int index = 0; //menu index
        int Kd = 250; // deferential constant
        float lastError = 0;

        float Ki = 4.0f; //integral constant
        int integral = 0;

        //---------------PID Loop------------------------
        while (true) {
            lval = dval;
            rval = dval;

            lightSampleProvider.fetchSample(lightSample, 0);
            e = lightSample[0];//offset

            if (e < 0.3 || e > 0.45) { // filtering out  noise, so that robot can go straight
                e -= 0.42;
                lastError = e - lastError;
                e += e;
                rval = (int) (dval + (k * kSym * e) + Kd * lastError + Ki * integral); //sensor reading are no symetrical, hence constant 1.7 adjust
                lval = (int) (dval - ((k * e) + Kd * lastError + Ki * integral));
                lastError = e;
            }

            lMotor.setSpeed(lval);
            rMotor.setSpeed(rval);
            lMotor.forward();
            rMotor.forward();
            if (MultiThreadingSync.getMode() == 2 || MultiThreadingSync.getMode() == 4) {
                Utility.display(MultiThreadingSync.getMode());
                break;
            }
        }
        if(MultiThreadingSync.getMode() == 2)
            avoid();
    }

    public static void avoid(){

        Utility.display("Avoid Launched");
        //----------Setting Up for circum navigation of obsticle ---------
        sMotor.rotate(-90, true);
        rMotor.setSpeed(40);
        lMotor.setSpeed(260);
        lMotor.forward();
        rMotor.forward();
        Delay.msDelay(850);

        rMotor.stop();
        lMotor.stop();

        //-----------PD Parameters-----------------------
        int dval = 200; // base motor value
        int lval = dval;
        int rval = dval;


        float e; // error term and sensor recorded value (dual use
        float kSym = 1.3f;
        boolean flag = true; // for breaking out of loops

        float kp = 500; //constant of proportionality

        int Kd = 300; // deferential constant
        float lastError = 0; //

        sonarSampleProvider.fetchSample(sonarSample, 0);

        while(flag){
            while(MultiThreadingSync.getMode()==2){//TODO break condition
                sonarSampleProvider.fetchSample(sonarSample,0);
                lightSampleProvider.fetchSample(lightSample, 0);

                while(lightSample[0] > 0.45){
                    Utility.display("Avoiding", sonarSample[0]);
                    sonarSampleProvider.fetchSample(sonarSample,0);
                    e = sonarSample[0];//offset

                    if (e < 0.03 || e > 0.05) { // filtering out  noise, so that robot can go straight
                        e -= 0.04;
                        lastError = e - lastError;
                        rval = (int) (dval + (kp * kSym * e) + Kd * lastError); //sensor reading are no symetrical, hence constant ksym adjust
                        lval = (int) (dval - ((kp * e) + Kd * lastError));
                        lastError = e;
                    }
                    lMotor.setSpeed(lval);
                    rMotor.setSpeed(rval);
                    lMotor.forward();
                    rMotor.forward();
                }

                rMotor.stop();
                lMotor.stop();
                sMotor.rotate(90);
                flag = false;
                break;
            }

        }
        //------------Getting Onto Line----------------
        rMotor.setSpeed(100);
        lMotor.setSpeed(100);
        rMotor.forward();
        lMotor.forward();

        lightSampleProvider.fetchSample(lightSample, 0);
        while(lightSample[0] < 0.60){
            lightSampleProvider.fetchSample(lightSample, 0);
            Utility.display("1" , lightSample[0]);
        }

        rMotor.stop();
        lMotor.stop();

        rMotor.setSpeed(20);
        lMotor.setSpeed(50);
        rMotor.backward();
        lMotor.forward();

        lightSampleProvider.fetchSample(lightSample, 0);
        while(lightSample[0] > 0.20){
            lightSampleProvider.fetchSample(lightSample, 0);
            Utility.display("2" , lightSample[0]);
        }

        rMotor.stop();
        lMotor.stop();

        //---------------------Once on the line--------------------------------------------------

        lightSampleProvider.fetchSample(lightSample, 0);
        while(lightSample[0] < 0.40){
            lightSampleProvider.fetchSample(lightSample, 0);
            Utility.display("3" , lightSample[0]);
        }

        pid();
    }
}

