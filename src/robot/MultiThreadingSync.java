package robot;

/**
 * DON'T EDIT THIS FILE UNLESS YOU KNOW WHAT YOU'RE DOING
 */
public class MultiThreadingSync {
    /*
    1 = line follower
    2 = avoid obstacle
     */
    public static int mode = 1;

    public static synchronized void setMode(int newMode){mode = newMode;}

    public static synchronized int getMode(){return mode;}
}
