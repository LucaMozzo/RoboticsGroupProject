import robot.*;
import utils.Utility;


/**
 * @author luca
 * Main class of the program
 */
public class Main {
    public static void main(String[] args) throws Exception{
        Thread pid = new PID();
        Thread avoid = new Avoid();
        Utility.setup();
        (new UltrasonicDetection()).start(pid, avoid);
        //FetchSamples.start();
    }
}
