import robot.*;
import utils.Utility;


/**
 * @author luca
 * Main class of the program
 */
public class Main {
    public static void main(String[] args) throws Exception{
        Utility.setup();
        /*Thread pid = new Thread();
        Thread avoid = new Thread();
        (new UltrasonicDetection()).start(pid, avoid);*/
        FetchSamples.start();
    }
}
