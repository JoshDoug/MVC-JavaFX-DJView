package djview;

import javafx.scene.control.ProgressBar;

/**
 * Created by jds on 12/01/2017.
 */
public class FXBeatBar extends ProgressBar implements Runnable {
    //what is this for? removing for now
    //private static final long serialVersionUID = 2L;
    ProgressBar progressBar;
    Thread thread;

    public FXBeatBar() {
        thread = new Thread(this);
        //setMaximum(100);
        // setMaxSize(); ?? dunno
        setMaxSize(100,100);
        thread.start();
    }

    public void run() {
        while(true) {
            double value = getProgress();
            value = value * .75;
            setProgress(value);
//            System.out.println("BeatBar Thread is running");
            try {
                Thread.sleep(50);
            } catch (Exception e) {};
        }
    }
}
