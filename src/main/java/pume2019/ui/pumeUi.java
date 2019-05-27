package pume2019.ui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class pumeUi extends Application {

    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane ap = new AnchorPane();
        VBox infoVb = new VBox();
        infoVb.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: black;");
        GridPane gp = new GridPane();
        gp.setHgap(20);
        gp.setVgap(30);
        gp.setPadding(new Insets(10, 10, 10, 10));
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: black;");
//        bp.setPrefSize(1100,800);
        bp.prefHeightProperty().bind(ap.heightProperty());
        bp.prefWidthProperty().bind(ap.widthProperty().subtract(infoVb.widthProperty().add(20)));

        VBox bpLeftVb = new VBox();
        bpLeftVb.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");
        bp.setLeft(bpLeftVb);
        bpLeftVb.setPadding(new Insets(5, 5, 5, 5));
        bpLeftVb.setSpacing(20);
//        bpLeftVb.setPrefWidth(150);
        Button tscBtn = new Button("Traditional \n"
                + "stand \n"
                + "characteristics");
        Button bioBtn = new Button("Biomasses");
        Button cbwBtn = new Button("Carbon \n"
                + "balance and \n"
                + "water use");
        tscBtn.setMaxWidth(Double.MAX_VALUE);
        bioBtn.setMaxWidth(Double.MAX_VALUE);
        cbwBtn.setMaxWidth(Double.MAX_VALUE);
        tscBtn.prefHeightProperty().bind(bpLeftVb.heightProperty().divide(3));
        bioBtn.prefHeightProperty().bind(bpLeftVb.heightProperty().divide(3));
        cbwBtn.prefHeightProperty().bind(bpLeftVb.heightProperty().divide(3));

        bpLeftVb.getChildren().addAll(tscBtn, bioBtn, cbwBtn);

        BorderPane nestedBp = new BorderPane();
        nestedBp.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: red;");
        bp.setCenter(nestedBp);

        GridPane tscGp = new GridPane();
        tscGp.setHgap(10);
        tscGp.setVgap(10);

        tscGp.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");

        Button treeCrownBtn = new Button("Tree Height, Crown base");
        Button diaBtn = new Button("Diameter");
        Button volBtn = new Button("Volume");
        Button basBtn = new Button("Basal area");
        Button stockBtn = new Button("Stocking density");
        Button remBtn = new Button("Removals");
        ObservableList<Button> tscBtns;
        tscBtns = FXCollections.observableArrayList();
        tscBtns.addAll(treeCrownBtn, diaBtn, volBtn, basBtn, stockBtn, remBtn);

        for (int i = 0; i < tscBtns.size(); i++) {
            Button btn = tscBtns.get(i);
            btn.prefWidthProperty().bind(tscGp.widthProperty().divide(tscBtns.size()));
            tscGp.add(btn, i, 0);
        }

        ObservableList<RadioButton> tscRbs;
        tscRbs = FXCollections.observableArrayList();
        RadioButton pineRb = new RadioButton("Pine");
        RadioButton spruceRb = new RadioButton("Spruce");
        RadioButton birchRb = new RadioButton("Birch");
        tscRbs.addAll(pineRb, spruceRb, birchRb);
        for (int i = 0; i < tscRbs.size(); i++) {
            RadioButton rb = tscRbs.get(i);
            rb.prefWidthProperty().bind(tscGp.widthProperty().divide(tscRbs.size()));
            tscGp.add(rb, i, 1, 2, 2);
        }

        GridPane bioGp = new GridPane();
        bioGp.setVisible(false);
        bioGp.setHgap(10);
        bioGp.setVgap(10);

        bioGp.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");

        Button totalBioBtn = new Button("Total biomass");
        Button needlesBtn = new Button("Needles");
        Button branchesBtn = new Button("Branches");
        Button stemBtn = new Button("Stem");
        Button fineRBtn = new Button("Fine roots");
        Button coarseRBtn = new Button("Coarse roots");
        ObservableList<Button> bioBtns;
        bioBtns = FXCollections.observableArrayList();
        bioBtns.addAll(totalBioBtn, needlesBtn, branchesBtn, stemBtn, fineRBtn, coarseRBtn);
        for (int i = 0; i < bioBtns.size(); i++) {
            Button btn = bioBtns.get(i);
            btn.prefWidthProperty().bind(bioGp.widthProperty().divide(bioBtns.size()));
            bioGp.add(btn, i, 0);
        }

        ObservableList<RadioButton> bioRbs;
        bioRbs = FXCollections.observableArrayList();
        RadioButton needlesRb = new RadioButton("Needles");
        RadioButton branchesRb = new RadioButton("Branches");
        RadioButton stemRb = new RadioButton("Stem");
        RadioButton fineRb = new RadioButton("Fine roots");
        RadioButton coarseRb = new RadioButton("Coarse roots");
        bioRbs.addAll(needlesRb, branchesRb, stemRb, fineRb, coarseRb);
        for (int i = 0; i < bioRbs.size(); i++) {
            RadioButton rb = bioRbs.get(i);
            rb.prefWidthProperty().bind(bioGp.widthProperty().divide(bioRbs.size()));
            bioGp.add(rb, i+1, 1, 2, 2);
        }

        nestedBp.setTop(tscGp);

        tscBtn.setOnAction(e -> {
            tscGp.setVisible(true);
            bioGp.setVisible(false);
            nestedBp.setTop(tscGp);
        });

        bioBtn.setOnAction(e -> {
            tscGp.setVisible(false);
            bioGp.setVisible(true);
            nestedBp.setTop(bioGp);
        });
        cbwBtn.setOnAction(e -> {
            tscGp.setVisible(false);
            bioGp.setVisible(false);
        });

        Label initSitLbl = new Label("Initial situation:");
        Label siteLbl = new Label("Site type:");
        Label weatherLbl = new Label("Weather:");
        Label managLbl = new Label("Management:");
        ComboBox treesCb = new ComboBox();
        ComboBox siteCb = new ComboBox();

        ToggleGroup weatherTg = new ToggleGroup();
        RadioButton weatherRbDef = new RadioButton("Default .csv");
        weatherRbDef.setToggleGroup(weatherTg);
        weatherRbDef.setSelected(true);
        RadioButton weatherRbCus = new RadioButton("Custom .csv");
        weatherRbCus.setToggleGroup(weatherTg);
        Button weatherBtn = new Button("Choose file");
        weatherBtn.setDisable(true);

        ToggleGroup managTg = new ToggleGroup();
        RadioButton managRbDef = new RadioButton("Standard \n"
                + "recommendations");
        managRbDef.setToggleGroup(managTg);
        managRbDef.setSelected(true);
        RadioButton managRbCus = new RadioButton("Custom .csv");
        managRbCus.setToggleGroup(managTg);
        Button managBtn = new Button("Choose file");
        managBtn.setDisable(true);

        Label yearsLbl = new Label("Years");
        final Spinner yearsSpin = new Spinner();
        String INITIAL_VALUE = "100";
        yearsSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,
                Integer.parseInt(INITIAL_VALUE)));
        yearsSpin.setEditable(true);
        yearsSpin.setPrefSize(60, 20);

        Button runBtn = new Button("Run Model");

        gp.add(initSitLbl, 0, 0);
        gp.add(treesCb, 1, 0);
        gp.add(siteLbl, 0, 1);
        gp.add(siteCb, 1, 1);
        gp.add(weatherLbl, 0, 2);
        gp.add(weatherRbDef, 1, 2);
        gp.add(weatherRbCus, 2, 2);
        gp.add(weatherBtn, 3, 2);
        gp.add(managLbl, 0, 3);
        gp.add(managRbDef, 1, 3);
        gp.add(managRbCus, 2, 3);
        gp.add(managBtn, 3, 3);
        gp.add(yearsLbl, 0, 4);
        gp.add(yearsSpin, 1, 4);
        gp.add(runBtn, 0, 5);

        infoVb.getChildren().add(gp);
        ap.getChildren().addAll(infoVb, bp);
        AnchorPane.setLeftAnchor(infoVb, 10d);
        AnchorPane.setRightAnchor(bp, 10d);

        weatherTg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (weatherRbCus.isSelected()) {
                    weatherBtn.setDisable(false);
                } else {
                    weatherBtn.setDisable(true);
                }
            }
        });

        managTg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (managRbCus.isSelected()) {
                    managBtn.setDisable(false);
                } else {
                    managBtn.setDisable(true);
                }
            }
        });

        EventHandler<KeyEvent> enterKeyEventHandler;
        enterKeyEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        Integer.parseInt(yearsSpin.getEditor().textProperty().get());
                        infoVb.requestFocus();
                    } catch (NumberFormatException e) {
                        yearsSpin.getEditor().textProperty().set(INITIAL_VALUE);
                        infoVb.requestFocus();
                    }
                }
            }
        };
        yearsSpin.getEditor().addEventHandler(KeyEvent.KEY_PRESSED, enterKeyEventHandler);

        yearsSpin.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!yearsSpin.getEditor().textProperty().get().matches("[0-9]*")) {
                if (!oldValue.matches("[0-9]*")) {
                    yearsSpin.getEditor().textProperty().set(INITIAL_VALUE);
                }
                Platform.runLater(() -> {
                    yearsSpin.getEditor().textProperty().set("100");
                });

                infoVb.requestFocus();
            }
        });

        scene = new Scene(ap, 1600, 800);

        primaryStage.setTitle("PuMe 2019");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    public static GridPane toGridPane(GridPane gp, ObservableList<Button> buttons) {
//        for (int i = 0; i < buttons.size(); i++) {
//            Button btn = buttons.get(i);
//            btn.prefWidthProperty().bind(gp.widthProperty().divide(buttons.size()));
//            gp.add(btn, i, 0);
//        }
//        return gp;
//    }
    public static void main(String[] args) {
        launch(args);
    }

}
