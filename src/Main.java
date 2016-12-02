import lejos.utility.Delay;
import robot.*;
import utils.Utility;

/**
 * @author luca
 * Main class of the program
 */
public class Main {
    public static void main(String[] args) throws Exception{
        Utility.setup();
        //PIDTuner.start();

        Thread pid = new PID();
        Thread avoid = new Avoid();
        Utility.setup();

        //detect curtains
        float[] sampleSonar = new float[Utility.sSensor.sampleSize()];
        Utility.sSensor.fetchSample(sampleSonar, 0);
        while(sampleSonar[0] > 0.3) {Utility.sSensor.fetchSample(sampleSonar, 0);
            Delay.msDelay(100);}
        while(sampleSonar[0] < 0.3) {Utility.sSensor.fetchSample(sampleSonar, 0);
            Delay.msDelay(100);} //30cm
        Delay.msDelay(200); //time to lift the curtain completely
        (new UltrasonicDetection()).start(pid, avoid);
        //FetchSamples.start();

    }
}
