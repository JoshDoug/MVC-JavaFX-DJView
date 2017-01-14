package djview;/**
 * Created by joshstringfellow on 08/01/2017.
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FXView extends Application implements BeatObserver, BPMObserver {
    BeatModelInterface model;
    ControllerInterface controller;
    Scene viewScene;
    GridPane viewPane;
    FXBeatBar beatBar;
    Label bpmOutputLabel;

    Scene controlScene;
    GridPane controlPane;
    Label bpmLabel;
    TextField bpmTextField;
    Button setBPMButton;
    Button increaseBPMButton;
    Button decreaseBPMButton;
    MenuBar menuBar;
    Menu menu;
    MenuItem startMenuItem;
    MenuItem stopMenuItem;

    @Override
    public void start(Stage primaryStage) {
        createView(primaryStage);

        Stage controlStage = new Stage();
        createControls(controlStage);
    }

    public void initialise() {
        launch();
    }

    public void populate(ControllerInterface controller, BeatModelInterface model) {
        this.controller = controller;
        this.model = model;
        model.registerObserver((BeatObserver)this);
        model.registerObserver((BPMObserver)this);
    }

    public void createView(Stage primaryStage) {
        // Create all FX Components here
        viewPane = new GridPane();
        viewScene = new Scene(viewPane, 200, 200);
        bpmOutputLabel = new Label("offline");
        beatBar = new FXBeatBar();
        beatBar.setProgress(0);

        viewPane.add(beatBar, 0, 0);
        viewPane.add(bpmOutputLabel, 0, 1);

        primaryStage.setTitle("View");
        primaryStage.setScene(viewScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    public void createControls(Stage controlStage) {
        // Create all FX Components here
        controlPane = new GridPane();
        controlScene = new Scene(controlPane, 100, 100);

        menuBar = new MenuBar();
        menu = new Menu("DJ Control");
        startMenuItem = new MenuItem("Start");
        menu.getItems().add(startMenuItem);

        startMenuItem.setOnAction(event -> controller.start());
        stopMenuItem = new MenuItem("Stop");
        menu.getItems().add(stopMenuItem);
        stopMenuItem.setOnAction(event -> controller.stop());

        MenuItem exit = new MenuItem("Quit");
        exit.setOnAction(event -> System.exit(0));
        menu.getItems().add(exit);
        menuBar.getMenus().add(menu);

        controlPane.add(menuBar, 0,0);

        bpmTextField = new TextField();
        bpmTextField.setPrefColumnCount(2);

        bpmLabel = new Label("Enter BPM");
        setBPMButton = new Button("Set");
        // Might set size here
        increaseBPMButton = new Button(">>");
        decreaseBPMButton = new Button("<<");
        //Not sure how this should be handled...
        //setBPMButton.addEventHandler(this);
        //increaseBPMButton.addActionListener(this);
        //decreaseBPMButton.addActionListener(this);

        controlPane.add(bpmLabel, 0, 1);
        controlPane.add(bpmTextField,1,1);
        controlPane.add(setBPMButton, 0,2,2,1);
        controlPane.add(decreaseBPMButton,0,3);
        controlPane.add(increaseBPMButton,1,3);

        controlStage.setTitle("Control");
        controlStage.setScene(controlScene);
        controlStage.show();
        controlStage.setOnCloseRequest(event -> System.exit(0));
    }

    public void enableStopMenuItem() {
        stopMenuItem.setDisable(false);
    }

    public void disableStopMenuItem() {
        stopMenuItem.setDisable(true);
    }

    public void enableStartMenuItem() {
        startMenuItem.setDisable(false);
    }

    public void disableStartMenuItem() {
        startMenuItem.setDisable(true);
    }

    public void updateBPM() {
        if (model != null) {
            int bpm = model.getBPM();
            if (bpm == 0) {
                if (bpmOutputLabel != null) {
                    bpmOutputLabel.setText("offline");
                }
            } else {
                if (bpmOutputLabel != null) {
                    bpmOutputLabel.setText("Current BPM: " + model.getBPM());
                }
            }
        }
    }

    public void updateBeat() {
        if (beatBar != null) {
            beatBar.setProgress(100);
        }
    }
}
