package djview;

/**
 * Created by joshstringfellow on 08/01/2017.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
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
        populate();
    }

    public void initialise() {
        launch();
    }

    public void populate() {
        this.model = new BeatModel();
		this.controller = new FXController(this.model, this);
        model.registerObserver((BeatObserver)this);
        model.registerObserver((BPMObserver)this);
        checkObservers();
    }

    public void checkObservers() {
        if(model == null) {
            System.out.println("Model is null");
        } else {
            System.out.println(model.getBPM());
        }
    }

    public void createView(Stage viewStage) {
        viewPane = new GridPane();
        viewPane.setPadding(new Insets(10, 10, 10, 10));
        viewPane.setHgap(10);
        viewPane.setVgap(10);
        viewScene = new Scene(viewPane, 150, 80);
        bpmOutputLabel = new Label("Offline");
        beatBar = new FXBeatBar();
        beatBar.setProgress(0);

        viewPane.add(beatBar, 0, 0);
        viewPane.add(bpmOutputLabel, 0, 1);

        viewStage.setTitle("View");
        viewStage.setScene(viewScene);
        viewStage.show();
        viewStage.setOnCloseRequest(e -> System.exit(0));
    }

    public void createControls(Stage controlStage) {
        BorderPane parentPane = new BorderPane();
        parentPane.setMinSize(300,300);
        controlPane = new GridPane();
        controlPane.setHgap(10);
        controlPane.setVgap(10);
        controlPane.setPadding(new Insets(10, 10, 10, 10));

        menuBar = new MenuBar();
        menu = new Menu("DJ Control");

        startMenuItem = new MenuItem("Start");
        menu.getItems().add(startMenuItem);
        startMenuItem.setOnAction(event -> controller.start());
        stopMenuItem = new MenuItem("Stop");
        stopMenuItem.setOnAction(event -> controller.stop());
        menu.getItems().add(stopMenuItem);
        MenuItem exit = new MenuItem("Quit");
        exit.setOnAction(event -> System.exit(0));

        menu.getItems().add(exit);
        menuBar.getMenus().add(menu);

        bpmTextField = new TextField();
        bpmLabel = new Label("Enter BPM: ");
        setBPMButton = new Button("Set");
        setBPMButton.setMinWidth(100);
        increaseBPMButton = new Button(">>");
        decreaseBPMButton = new Button("<<");

        setBPMButton.setOnAction(event -> {
            int bpm = Integer.parseInt(bpmTextField.getText());
            controller.setBPM(bpm);
        });
        increaseBPMButton.setOnAction(event -> controller.increaseBPM());
        decreaseBPMButton.setOnAction(event -> controller.decreaseBPM());

        parentPane.setTop(menuBar);
        parentPane.setCenter(controlPane);
        controlPane.add(bpmLabel, 0, 0,2,1);
        controlPane.add(bpmTextField,2,0,2,1);
        controlPane.add(setBPMButton, 1,1,2,1);
        controlPane.add(decreaseBPMButton,0,1,1,1);
        controlPane.add(increaseBPMButton,3,1,2,1);

        controlScene = new Scene(parentPane, 275,120);
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

    public void onEvent(Button button) {
        if (button == setBPMButton) {
            int bpm = Integer.parseInt(bpmTextField.getText());
            controller.setBPM(bpm);
        } else if (button == increaseBPMButton) {
            controller.increaseBPM();
        } else if (button == decreaseBPMButton) {
            controller.decreaseBPM();
        }
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
