package robot;

/**
 * Created by lucam on 23/11/2016.
 */
public class Avoid extends Thread {

    private Thread ultrasonicThread;

    public Avoid(Thread ultrasonicThread){
        this.ultrasonicThread = ultrasonicThread;
    }

    @Override
    public void run(){
        while(MultiThreadingSync.getMode() == 2){
            //avoid the obstacle
            //use setLineFollowerMode to jump out of the method
        }
        stop();
    }
}
