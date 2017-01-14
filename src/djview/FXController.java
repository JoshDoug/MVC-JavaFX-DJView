package djview;

/**
 * Created by joshstringfellow on 08/01/2017.
 */
public class FXController implements ControllerInterface {
    private BeatModelInterface model;
    private FXView view;

    FXController(BeatModelInterface model, FXView view) {
        this.model = model;
        this.view = view;
        System.out.println("Does this method ever finish");
        view.disableStopMenuItem();
        view.enableStartMenuItem();
        model.initialize();
    }

    public void start() {
        model.on();
        view.disableStartMenuItem();
        view.enableStopMenuItem();
    }

    public void stop() {
        model.off();
        view.disableStopMenuItem();
        view.enableStartMenuItem();
    }

    public void increaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm + 1);
    }

    public void decreaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm - 1);
    }

    public void setBPM(int bpm) {
        model.setBPM(bpm);
    }

}
