package djview;

/**
 * Created by joshstringfellow on 08/01/2017.
 */
public class FXController implements ControllerInterface {
    BeatModelInterface model;
    FXView view;

    public FXController(BeatModelInterface model) {
        this.model = model;
        view = new FXView();
        System.out.println("Initialising JavaFX Class");
        view.initialise();
        System.out.println("Create the GUI");
        view.populate(this,model);
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
