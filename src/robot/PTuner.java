package robot;

import lejos.hardware.Button;
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
import utils.Utility;

/**
 * @author luca
 * Class provides methods for following the line
 */
public final class PTuner {

    public static void start() {


        Port port = LocalEV3.get().getPort("S1");
        RegulatedMotor rMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        RegulatedMotor lMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        SensorModes lSensor = new EV3ColorSensor(port);
        SampleProvider colourSampleProvider = lSensor.getMode("Red");


        float[] sample = new float[colourSampleProvider.sampleSize()];

        int lval;
        int rval;
        final int dval = 200; // base motor value
        float k = 320; //constant of proportionality
        double e; // error term and sensor recorded value (dual use

        float kSym = 1.3f;


        while (true) {
            while (Button.getButtons() == 0) {
                colourSampleProvider.fetchSample(sample, 0);

                lval = dval;
                rval = dval;
                e = sample[0];
                if (e < 0.3 || e > 0.45) { // filtering out  noise, so that robot can go straight
                    e -= 0.375;
                    lval = (int) (dval - (k * e)); //sensor reading are no symetrical, hence constant 1.7 adjust
                    rval = (int) (dval + (k * kSym * e));

                /*String[] strings = {"lval: ", "rval: ", "sensor: "};
                float[] floats = {lval, rval, sample[0]};
                Utility.display(strings, floats);*/
                }
                lMotor.setSpeed(lval);
                rMotor.setSpeed(rval);
                lMotor.forward();
                rMotor.forward();
                //Delay.msDelay(500);
            }

            if (Button.getButtons() == Button.ID_UP)
                kSym += 0.1;
            else if (Button.getButtons() == Button.ID_DOWN) {
                kSym -= 0.1;
            }
            else if (Button.getButtons() == Button.ID_RIGHT)
                k += 10;
            else if (Button.getButtons() == Button.ID_LEFT)
                k -= 10;
            else if(Button.getButtons() == Button.ID_ENTER) {
                Delay.msDelay(500);
                lMotor.setSpeed(0);
                rMotor.setSpeed(0);
                lMotor.forward();
                rMotor.forward();
                while (Button.getButtons() != Button.ID_ENTER){

                    Delay.msDelay(200);
                }
            }



            String[] str = {"Kprop: ", "k: "};
            float[] ints = {kSym, k};
            utils.Utility.display(str, ints);

            Delay.msDelay(100);
        }
    }
}
