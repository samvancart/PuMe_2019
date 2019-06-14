package pume2019.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.event.HyperlinkEvent;
import pume2019.domain.Heath;
import pume2019.domain.Info;
import pume2019.domain.InitStand;
import pume2019.domain.SiteInfo;
import pume2019.domain.Tree;

public class PumeUi extends Application {

    private Scene scene;
    private Info info;
    private InitStand initStand;
    private SiteInfo siteInfo;
    private String defPath;
    private ButtonController bc;
    private Button clickedBtn;

    @Override
    public void init() {
        bc = new ButtonController();
        String currentDirectory = System.getProperty("user.dir");
        defPath = currentDirectory + "\\src\\main\\resources\\R-Portable\\Rprebas_examples-master\\inputs";
        info = new Info();
        info.setWeatherPath(defPath + "\\weather.csv");
        info.setManagPath(defPath + "\\thinning.csv");
    }

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
        Button cwgBtn = new Button("Carbon "
                + "balance, \n"
                + "water use \n"
                + "and growth");
        tscBtn.setMaxWidth(Double.MAX_VALUE);
        bioBtn.setMaxWidth(Double.MAX_VALUE);
        cwgBtn.setMaxWidth(Double.MAX_VALUE);
        tscBtn.prefHeightProperty().bind(bpLeftVb.heightProperty().divide(3));
        bioBtn.prefHeightProperty().bind(bpLeftVb.heightProperty().divide(3));
        cwgBtn.prefHeightProperty().bind(bpLeftVb.heightProperty().divide(3));

        bpLeftVb.getChildren().addAll(tscBtn, bioBtn, cwgBtn);

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
        treeCrownBtn.setId("11 14");
        Button diaBtn = new Button("Diameter");
        diaBtn.setId("12");
        Button volBtn = new Button("Volume");
        volBtn.setId("30");
        Button basBtn = new Button("Basal area");
        basBtn.setId("13");
        Button stockBtn = new Button("Stocking density");
        stockBtn.setId("17");
        Button remBtn = new Button("Removals");
        ObservableList<Button> tscBtns;
        tscBtns = FXCollections.observableArrayList();
        tscBtns.addAll(treeCrownBtn, diaBtn, volBtn, basBtn, stockBtn, remBtn);
        bc.addBtns(tscBtns, tscGp);
        bc.defineBtnHandler(tscBtns);

        ObservableList<RadioButton> treeRbs;
        treeRbs = FXCollections.observableArrayList();
        RadioButton pineRb = new RadioButton("Pine");
        pineRb.setId("1");
        RadioButton spruceRb = new RadioButton("Spruce");
        spruceRb.setId("2");
        RadioButton birchRb = new RadioButton("Birch");
        birchRb.setId("3");
        treeRbs.addAll(pineRb, spruceRb, birchRb);
        bc.addRbs(treeRbs, tscGp, 0);

        ObservableList<RadioButton> remRbs;
        remRbs = FXCollections.observableArrayList();
        RadioButton cuttingsRb = new RadioButton("Cuttings");
        cuttingsRb.setId("37");
        RadioButton mortalityRb = new RadioButton("Mortality");
        mortalityRb.setId("42");
        remRbs.addAll(pineRb, spruceRb, birchRb, cuttingsRb, mortalityRb);

        ArrayList<ObservableList> tscRbtns;
        tscRbtns = new ArrayList<>();
        tscRbtns.add(remRbs);
        tscRbtns.add(treeRbs);

        treeCrownBtn.setOnAction(e -> {
            bc.removeRbtns(tscRbtns, tscGp);
            bc.addRbs(treeRbs, tscGp, 0);
        });
        diaBtn.setOnAction(e -> {
            bc.removeRbtns(tscRbtns, tscGp);
            bc.addRbs(treeRbs, tscGp, 0);
        });
        volBtn.setOnAction(e -> {
            bc.removeRbtns(tscRbtns, tscGp);
            bc.addRbs(treeRbs, tscGp, 0);
        });
        basBtn.setOnAction(e -> {
            bc.removeRbtns(tscRbtns, tscGp);
            bc.addRbs(treeRbs, tscGp, 0);
        });
        stockBtn.setOnAction(e -> {
            bc.removeRbtns(tscRbtns, tscGp);
            bc.addRbs(treeRbs, tscGp, 0);
        });
        remBtn.setOnAction(e -> {
            bc.removeRbtns(tscRbtns, tscGp);
            bc.addRbs(remRbs, tscGp, 0);
        });

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

        Button totalBioBtn = new Button("Total biomass"); // Ei Id:tä, koska lasketaan kaikkien summasta
        Button foliageBtn = new Button("Foliage mass");
        foliageBtn.setId("33");
        Button branchesBtn = new Button("Branch mass");
        branchesBtn.setId("24");
        Button stemBtn = new Button("Stem mass");
        stemBtn.setId("31");
        Button fineBtn = new Button("Fine root mass");
        fineBtn.setId("25");
        Button coarseBtn = new Button("Coarse root mass");
        coarseBtn.setId("32");
        ObservableList<Button> bioBtns;
        bioBtns = FXCollections.observableArrayList();
        bioBtns.addAll(totalBioBtn, foliageBtn, branchesBtn, stemBtn, fineBtn, coarseBtn);
        bc.addBtns(bioBtns, bioGp);
        bc.defineBtnHandler(bioBtns);

        ObservableList<RadioButton> bioRbs;
        bioRbs = FXCollections.observableArrayList();
        RadioButton foliageRb = new RadioButton("Foliage mass");
        foliageRb.setId("33");
        RadioButton branchesRb = new RadioButton("Branch mass");
        branchesRb.setId("24");
        RadioButton stemRb = new RadioButton("Stem mass");
        stemRb.setId("31");
        RadioButton fineRb = new RadioButton("Fine root mass");
        fineRb.setId("25");
        RadioButton coarseRb = new RadioButton("Coarse root mass");
        coarseRb.setId("32");
        bioRbs.addAll(foliageRb, branchesRb, stemRb, fineRb, coarseRb);

        ArrayList<ObservableList> bioRbtns;
        bioRbtns = new ArrayList<>();
        bioRbtns.add(bioRbs);

        totalBioBtn.setOnAction(e -> {
            bc.removeRbtns(bioRbtns, bioGp);
            bc.addRbs(bioRbs, bioGp, 1);
        });
        foliageBtn.setOnAction(e -> {
            bc.removeRbtns(bioRbtns, bioGp);
        });
        branchesBtn.setOnAction(e -> {
            bc.removeRbtns(bioRbtns, bioGp);
        });
        stemBtn.setOnAction(e -> {
            bc.removeRbtns(bioRbtns, bioGp);
        });
        fineBtn.setOnAction(e -> {
            bc.removeRbtns(bioRbtns, bioGp);
        });
        coarseBtn.setOnAction(e -> {
            bc.removeRbtns(bioRbtns, bioGp);
        });

        GridPane cwgGp = new GridPane();
        cwgGp.setVisible(false);
        cwgGp.setHgap(10);
        cwgGp.setVgap(10);

        cwgGp.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");

        Button cbBtn = new Button("Carbon Balance");
        // id?
        Button gppBtn = new Button("GPP");
        gppBtn.setId("44");
        Button wuBtn = new Button("Water use");
        Button vgBtn = new Button("Volume growth");
        vgBtn.setId("43");
        ObservableList<Button> cwgBtns;
        cwgBtns = FXCollections.observableArrayList();
        cwgBtns.addAll(cbBtn, gppBtn, wuBtn, vgBtn);
        bc.addBtns(cwgBtns, cwgGp);
        bc.defineBtnHandler(cwgBtns);

        ToggleGroup cbTg = new ToggleGroup();
        ObservableList<RadioButton> cbRbs;
        cbRbs = FXCollections.observableArrayList();

        RadioButton ppRb = new RadioButton("Potential photosynthesis");
        ppRb.setId("6");
        RadioButton gppRb = new RadioButton("GPP");
        gppRb.setId("10");
        RadioButton nppRb = new RadioButton("NPP");
        nppRb.setId("18");
        nppRb.setToggleGroup(cbTg);
        RadioButton arRb = new RadioButton("Autotrophic respiration");
        arRb.setId("9");
        arRb.setToggleGroup(cbTg);
        cbRbs.addAll(ppRb, gppRb, nppRb, arRb);

        ObservableList<RadioButton> wuRbs;
        wuRbs = FXCollections.observableArrayList();

        RadioButton evapRb = new RadioButton("Evapotranspiration");
        evapRb.setId("22");
        RadioButton summerSoilRb = new RadioButton("Summer soil water");
        summerSoilRb.setId("41");
        wuRbs.addAll(evapRb, summerSoilRb);

        ArrayList<ObservableList> cwgRbtns;
        cwgRbtns = new ArrayList<>();
        cwgRbtns.add(wuRbs);
        cwgRbtns.add(treeRbs);
        cwgRbtns.add(cbRbs);

        cbBtn.setOnAction(e -> {
            bc.removeRbtns(cwgRbtns, cwgGp);
            bc.addRbs(cbRbs, cwgGp, 0);
        });
        gppBtn.setOnAction(e -> {
            bc.removeRbtns(cwgRbtns, cwgGp);
            bc.addRbs(treeRbs, cwgGp, 0);
        });
        wuBtn.setOnAction(e -> {
            bc.removeRbtns(cwgRbtns, cwgGp);
            bc.addRbs(wuRbs, cwgGp, 0);
        });
        vgBtn.setOnAction(e -> {
            bc.removeRbtns(cwgRbtns, cwgGp);
            bc.addRbs(treeRbs, cwgGp, 0);
        });

        nestedBp.setTop(tscGp);

        tscBtn.setOnAction(e -> {
            if (clickedBtn != null) {
                clickedBtn.setStyle("");
            }
            tscGp.setVisible(true);
            cwgGp.setVisible(false);
            bioGp.setVisible(false);
            nestedBp.setTop(tscGp);
        });

        bioBtn.setOnAction(e -> {
            if (clickedBtn != null) {
                clickedBtn.setStyle("");
            }
            tscGp.setVisible(false);
            cwgGp.setVisible(false);
            bioGp.setVisible(true);
            nestedBp.setTop(bioGp);
        });
        cwgBtn.setOnAction(e -> {
            if (clickedBtn != null) {
                clickedBtn.setStyle("");
            }
            tscGp.setVisible(false);
            bioGp.setVisible(false);
            cwgGp.setVisible(true);
            nestedBp.setTop(cwgGp);
        });

        Label initSitLbl = new Label("Initial situation:");
        Label siteLbl = new Label("Site type:");
        Label weatherLbl = new Label("Weather:");
        Label managLbl = new Label("Management:");
        initStand = new InitStand();
        ComboBox treesCb = new ComboBox(initStand.getTrees());
        treesCb.getSelectionModel().selectFirst();
        siteInfo = new SiteInfo();
        ComboBox siteCb = new ComboBox(siteInfo.getHeaths());
        siteCb.getSelectionModel().selectFirst();

        ToggleGroup weatherTg = new ToggleGroup();
        RadioButton weatherRbDef = new RadioButton("Default .csv");
        weatherRbDef.setToggleGroup(weatherTg);
        weatherRbDef.setSelected(true);
        RadioButton weatherRbCus = new RadioButton("Custom .csv");
        weatherRbCus.setToggleGroup(weatherTg);
        Button weatherBtn = new Button("Choose file");
        weatherBtn.setMaxSize(80, 20);
        weatherBtn.setDisable(true);

        ToggleGroup managTg = new ToggleGroup();
        RadioButton managRbDef = new RadioButton("Standard \n"
                + "recommendations");
        managRbDef.setToggleGroup(managTg);
        managRbDef.setSelected(true);
        RadioButton managRbCus = new RadioButton("Custom .csv");
        managRbCus.setToggleGroup(managTg);
        Button managBtn = new Button("Choose file");
        managBtn.setMaxSize(80, 20);
        managBtn.setDisable(true);

        Label yearsLbl = new Label("Years:");
        final Spinner yearsSpin = new Spinner();
        String INITIAL_VALUE = "100";
        yearsSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200,
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

        runBtn.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                info.setHeath((Heath) siteCb.getValue());
                info.setTree((Tree) treesCb.getValue());
                info.setYears(yearsSpin.getEditor().textProperty().get());
                System.out.println("init stand: " + info.getTree());
                System.out.println("site type: " + info.getHeath());
                System.out.println("weather path: " + info.getWeatherPath());
                System.out.println("manag path: " + info.getManagPath());
                System.out.println("years: " + info.getYears());
            }
        });

        weatherBtn.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    String path[] = file.getPath().split("\\\\");
                    info.setWeatherPath(file.getPath());
                    if (!path[0].equals("")) {
                        weatherBtn.setText(path[path.length - 1]);
                    }
                }
            }
        });

        managBtn.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    String path[] = file.getPath().split("\\\\");
                    info.setManagPath(file.getPath());
                    if (!path[0].equals("")) {
                        managBtn.setText(path[path.length - 1]);
                    }
                }
            }
        });

        weatherTg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (weatherRbCus.isSelected()) {
                    weatherBtn.setDisable(false);
                } else {
                    weatherBtn.setDisable(true);
                    weatherBtn.setText("Choose File");
                    info.setWeatherPath(defPath + "\\weather.csv");
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
                    managBtn.setText("Choose File");
                    info.setManagPath(defPath + "\\thinning.csv");
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

    private class ButtonController implements EventHandler<Event> {

        public void addBtns(ObservableList<Button> btns, GridPane gp) {
            for (int i = 0; i < btns.size(); i++) {
                Button btn = btns.get(i);
                btn.prefWidthProperty().bind(gp.widthProperty().divide(btns.size()));
                gp.add(btn, i, 0);
            }
        }

        public void addRbs(ObservableList<RadioButton> rbs, GridPane gp, int startIndex) {
            for (int i = 0; i < rbs.size(); i++) {
                RadioButton rb = rbs.get(i);
                rb.prefWidthProperty().bind(gp.widthProperty().divide(rbs.size()));
                gp.add(rb, i + startIndex, 1, 2, 2);
            }
        }

        public void removeRbtns(ArrayList<ObservableList> rBtns, GridPane gp) {
            for (int i = 0; i < rBtns.size(); i++) {
                ObservableList btns = rBtns.get(i);
                gp.getChildren().removeAll(btns);
            }
        }

        public void defineBtnHandler(ObservableList<Button> btns) {
            btns.forEach(btn -> btn.addEventHandler(MouseEvent.MOUSE_CLICKED, new ButtonController()));
        }

        public void clearStyles() {

        }

        @Override
        public void handle(Event evt) {
            System.out.println(((Control) evt.getSource()).getId());
            if (clickedBtn != null) {
                clickedBtn.setStyle("");
            }
            clickedBtn = (Button) ((Control) evt.getSource());
            clickedBtn.setStyle("-fx-background-color: burlywood");

        }
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
