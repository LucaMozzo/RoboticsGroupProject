import robot.LineFollower;
import robot.PTuner;
import robot.TestLight;


/**
 * @author luca
 * Main class of the program
 */
public class Main {
    public static void main(String[] args){
        //(new Thread(new UltrasonicDetection())).start(); ultrasonic
        PTuner.setup();
        PTuner.start();

        //TestEncoder.start();



    }
}
