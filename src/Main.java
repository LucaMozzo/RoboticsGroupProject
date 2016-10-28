import robot.LineFollower;
import robot.UltrasonicDetection;

/**
 * @author luca
 * Main class of the program
 */
public class Main {
    public static void main(String[] args){
        (new Thread(new UltrasonicDetection())).start();

        LineFollower.start();

        //TestEncoder.start();



    }
}
