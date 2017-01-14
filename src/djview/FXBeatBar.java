package djview;

import javafx.scene.control.ProgressBar;

/**
 * Created by jds on 12/01/2017.
 */
public class FXBeatBar extends ProgressBar implements Runnable {
    ProgressBar progressBar;
    private Thread thread;

    public FXBeatBar() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        System.out.println("Progress bar set to run");
        while(true) {
            double value = getProgress();
            value = value * .75;
            setProgress(value);
            try {
                Thread.sleep(15);
            } catch (Exception e) {};
        }
    }
}
