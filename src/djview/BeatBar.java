package djview;

import javax.swing.*;

public class BeatBar extends JProgressBar implements Runnable {
	//what is this for? removing for now
	//private static final long serialVersionUID = 2L;
    JProgressBar progressBar;
	Thread thread;

	public BeatBar() {
		thread = new Thread(this);
		setMaximum(100);
		thread.start();
	}

	public void run() {
		while(true) {
			int value = getValue();
			value = (int)(value * .75);
			setValue(value);
			try {
				Thread.sleep(50);
			} catch (Exception e) {};
		}
	}
}
