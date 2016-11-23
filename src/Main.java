import robot.*;
import utils.Utility;


/**
 * @author luca
 * Main class of the program
 */
public class Main {
    public static void main(String[] args){
        //(new Thread(new UltrasonicDetection())).start(); ultrasonic
       // PTuner.setup();
       // PTuner.start();
       //FetchSamples.start();
        Utility.setup();
        FetchSamples.start();
        //TestEncoder.start();



    }
}
