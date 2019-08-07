package pume2019.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pume2019.controllers.ButtonController;
import pume2019.dataHandler.MyServerSocket;
import pume2019.dataHandler.PumeChartHandler;
import pume2019.dataHandler.PumeErrorHandler;
import pume2019.dataHandler.RFileHandler;
import pume2019.dataHandler.RFunctions;
import pume2019.dataHandler.RInputHandler;
import pume2019.dataHandler.ResultHandler;
import pume2019.domain.FileChooserButton;
import pume2019.domain.Heath;
import pume2019.domain.Info;
import pume2019.domain.InitStand;
import pume2019.domain.Management;
import pume2019.domain.Notification;
import pume2019.domain.Notifications;
import pume2019.domain.PumeButton;
import pume2019.domain.PumeId;
import pume2019.domain.Thinning;
import pume2019.domain.SiteInfo;
import pume2019.domain.Tree;

public class PumeUi extends Application {

    private Scene scene;
    private Info info;
    private InitStand initStand;
    private SiteInfo siteInfo;
    private Management manag;
    private String defPath;
    private ButtonController bc;
    private Button clickedBtn;
    private RFileHandler fileHandler;
    private RFunctions functions;
    private RInputHandler inputHandler;
    private MyServerSocket server;
    private HashMap<Integer, List<String>> pineMap, spruceMap, birchMap;
    private List<Map<Integer, List<String>>> maps;
    private ResultHandler rh;
    private PumeChartHandler pumeChartHandler;
    private PumeButton pumeBtn;
    private List<Button> totalBioMassBtns;
//    private String id;
    private PumeId id = new PumeId();
    private PumeErrorHandler errorHandler = new PumeErrorHandler();
    private List<ObservableList<Object>> checkBoxesList;
    private File initBtnFile;
    private File weatherBtnFile;
    private File managBtnFile;
    private FileChooserButton fcb = new FileChooserButton();
    private Notifications notifications = new Notifications();

    @Override
    public void init() throws IOException {
        bc = new ButtonController(new ButtonHandler());
        String currentDirectory = System.getProperty("user.dir");
        defPath = currentDirectory + "\\src\\main\\resources\\R-Portable\\Rprebas_examples-master\\inputs";
        info = new Info();
        info.setWeatherPath(defPath + "\\weather.csv");
        info.setManagPath(defPath + "\\thinning.csv");
        info.setInitPath(defPath + "\\initVar.csv");
        fileHandler = new RFileHandler("src\\main\\resources\\R-Portable\\input.R");
        functions = new RFunctions();
        inputHandler = new RInputHandler();
        rh = new ResultHandler();

    }

    @Override
    public void stop() {
        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                System.out.println("Close server " + e);
            }
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

//      Initialize
        init();

        // Background and style       
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

        //Buttons 1
        Button treeCrownBtn = new Button("Tree Height, Crown base");
        treeCrownBtn.setId("11");
        rh.addIdToMap("11", 1);
        rh.addIdToMap("14", 1);
        treeCrownBtn.setTooltip(new Tooltip("Tree Height, Crown base"));
        Button diaBtn = new Button("Diameter");
        diaBtn.setId("12");
        rh.addIdToMap(diaBtn.getId(), 1);
        Button volBtn = new Button("Volume");
        volBtn.setId("30");
        rh.addIdToMap(volBtn.getId(), 2);
        Button basBtn = new Button("Basal area");
        basBtn.setId("13");
        rh.addIdToMap(basBtn.getId(), 2);
        Button stockBtn = new Button("Stocking density");
        stockBtn.setId("17");
        rh.addIdToMap(stockBtn.getId(), 2);
        Button remBtn = new Button("Removals");
        remBtn.setId("37");
        rh.addIdToMap(remBtn.getId(), 2);

        ObservableList<Button> tscBtns;
        tscBtns = FXCollections.observableArrayList();
        tscBtns.addAll(treeCrownBtn, diaBtn, volBtn, basBtn, stockBtn, remBtn);
        bc.addBtns(tscBtns, tscGp);
        bc.defineBtnHandler(tscBtns);

        //Tree checkboxes    
        ObservableList<Object> treeObs;
        treeObs = FXCollections.observableArrayList();
        CheckBox pineChb = new CheckBox("Pine");
        pineChb.setId("1");
        CheckBox spruceChb = new CheckBox("Spruce");
        spruceChb.setId("2");
        CheckBox birchChb = new CheckBox("Birch");
        birchChb.setId("3");
        treeObs.addAll(pineChb, spruceChb, birchChb);
        bc.addOBtns(treeObs, tscGp, 0);

        // Tree ActionEvent handlers
//        Pine
        pineChb.setOnAction(e -> {
            System.out.println(id.getId());
            int intId = Integer.parseInt(id.getId());
            if (pineChb.isSelected()) {
                if (intId == 37) {
                    pumeChartHandler.removeFromStackedBarChart(0);
                }
                pumeChartHandler.addTreeToChart(pineMap, intId, "Pine", 1);
            }
            if (!pineChb.isSelected()) {
                if (intId == 37) {
                    pumeChartHandler.removeFromStackedBarChart(1);
                    bc.areNoneSelectedRemovals(treeObs, pumeChartHandler, nestedBp);
                }
                pumeChartHandler.removeTreeFromChart(1);

            }
        });
//      Spruce
        spruceChb.setOnAction(e -> {
            System.out.println(id);
            int intId = Integer.parseInt(id.getId());
            if (spruceChb.isSelected()) {
                if (intId == 37) {
                    pumeChartHandler.removeFromStackedBarChart(0);
                }
                pumeChartHandler.addTreeToChart(spruceMap, intId, "Spruce", 2);
            }
            if (!spruceChb.isSelected()) {
                if (intId == 37) {
                    pumeChartHandler.removeFromStackedBarChart(2);
                    bc.areNoneSelectedRemovals(treeObs, pumeChartHandler, nestedBp);
                }
                pumeChartHandler.removeTreeFromChart(2);
            }
        });
//      Birch
        birchChb.setOnAction(e -> {
            System.out.println(id);
            int intId = Integer.parseInt(id.getId());
            if (birchChb.isSelected()) {
                if (intId == 37) {
                    pumeChartHandler.removeFromStackedBarChart(0);
                }
                pumeChartHandler.addTreeToChart(birchMap, intId, "Birch", 3);
            }
            if (!birchChb.isSelected()) {
                if (intId == 37) {
                    pumeChartHandler.removeFromStackedBarChart(3);
                    bc.areNoneSelectedRemovals(treeObs, pumeChartHandler, nestedBp);
                }
                pumeChartHandler.removeTreeFromChart(3);
            }
        });

        //RadioButtons    
        ToggleGroup remTg = new ToggleGroup();
        ObservableList<Object> remObs;
        remObs = FXCollections.observableArrayList();
        RadioButton cuttingsRb = new RadioButton("Cuttings");
        cuttingsRb.setId("37");
        rh.addIdToMap(cuttingsRb.getId(), 2);
        cuttingsRb.setToggleGroup(remTg);
        RadioButton mortalityRb = new RadioButton("Mortality");
        mortalityRb.setId("42");
        rh.addIdToMap(mortalityRb.getId(), 2);
        mortalityRb.setToggleGroup(remTg);
        remObs.addAll(pineChb, spruceChb, birchChb, cuttingsRb, mortalityRb);

        ArrayList<ObservableList> tscObtns;
        tscObtns = new ArrayList<>();
        tscObtns.add(remObs);
        tscObtns.add(treeObs);

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

        //Buttons 2
        Button totalBioBtn = new Button("Total biomass"); // Ei Id:tä, koska lasketaan kaikkien summasta -> id=47
        totalBioBtn.setId("47");
        rh.addIdToMap(totalBioBtn.getId(), 3);
        Button foliageBtn = new Button("Foliage mass");
        foliageBtn.setId("33");
        rh.addIdToMap(foliageBtn.getId(), 2);
        Button branchesBtn = new Button("Branch mass");
        branchesBtn.setId("24");
        rh.addIdToMap(branchesBtn.getId(), 2);
        Button stemBtn = new Button("Stem mass");
        stemBtn.setId("31");
        rh.addIdToMap(stemBtn.getId(), 2);
        Button fineBtn = new Button("Fine root mass");
        fineBtn.setId("25");
        rh.addIdToMap(fineBtn.getId(), 2);
        Button coarseBtn = new Button("Coarse root mass");
        coarseBtn.setId("32");
        rh.addIdToMap(coarseBtn.getId(), 2);
        ObservableList<Button> bioBtns;
        bioBtns = FXCollections.observableArrayList();
        bioBtns.addAll(totalBioBtn, foliageBtn, branchesBtn, stemBtn, fineBtn, coarseBtn);
        bc.addBtns(bioBtns, bioGp);
        bc.defineBtnHandler(bioBtns);

        //Checkboxes for Buttons 2
        ObservableList<Object> bioObs;
        bioObs = FXCollections.observableArrayList();
        CheckBox foliageChb = new CheckBox("Foliage mass");
        foliageChb.setId("33");
        CheckBox branchesChb = new CheckBox("Branch mass");
        branchesChb.setId("24");
        CheckBox stemChb = new CheckBox("Stem mass");
        stemChb.setId("31");
        CheckBox fineChb = new CheckBox("Fine root mass");
        fineChb.setId("25");
        CheckBox coarseChb = new CheckBox("Coarse root mass");
        coarseChb.setId("32");
        bioObs.addAll(foliageChb, branchesChb, stemChb, fineChb, coarseChb);

        ArrayList<ObservableList> bioObtns;
        bioObtns = new ArrayList<>();
        bioObtns.add(bioObs);
        bioObtns.add(treeObs);

        //Total Biomass array
        totalBioMassBtns = new ArrayList<>();
        totalBioMassBtns.addAll(bioBtns);
        totalBioMassBtns.remove(0);

//ActionEvent handlers 2
        totalBioBtn.setOnAction(e -> {
            bc.removeRbtns(bioObtns, bioGp);
            bc.addOBtns(bioObs, bioGp, 1);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("kg C/ha");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createStackedAreaChartOfBiomass(nestedBp, totalBioMassBtns);

        });
        foliageBtn.setOnAction(e -> {
            bc.removeRbtns(bioObtns, bioGp);
            bc.addOBtns(treeObs, bioGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("kg C/ha");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);
        });
        branchesBtn.setOnAction(e -> {
            bc.removeRbtns(bioObtns, bioGp);
            bc.addOBtns(treeObs, bioGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("kg C/ha");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);
        });
        stemBtn.setOnAction(e -> {
            bc.removeRbtns(bioObtns, bioGp);
            bc.addOBtns(treeObs, bioGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("kg C/ha");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);
        });
        fineBtn.setOnAction(e -> {
            bc.removeRbtns(bioObtns, bioGp);
            bc.addOBtns(treeObs, bioGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("kg C/ha");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);
        });
        coarseBtn.setOnAction(e -> {
            bc.removeRbtns(bioObtns, bioGp);
            bc.addOBtns(treeObs, bioGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("kg C/ha");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);
        });

        // Total biomass Checkboxes
//      Foliage        
        foliageChb.setOnAction(e -> {
            System.out.println(id);
            if (foliageChb.isSelected()) {
                int intId = 33;
                pumeChartHandler.removeMassFromBioChart(0);
                pumeChartHandler.addMassToBioChart(maps, intId, "Foliage mass", 1);
            }
            if (!foliageChb.isSelected()) {
                pumeChartHandler.removeMassFromBioChart(1);
                bc.areNoneSelected(bioObs, pumeChartHandler, nestedBp, totalBioMassBtns);
            }
        });
//      Branches
        branchesChb.setOnAction(e -> {
            System.out.println(id);
            if (branchesChb.isSelected()) {
                int intId = 24;
                pumeChartHandler.removeMassFromBioChart(0);
                pumeChartHandler.addMassToBioChart(maps, intId, "Branch mass", 2);
            }
            if (!branchesChb.isSelected()) {
                pumeChartHandler.removeMassFromBioChart(2);
                bc.areNoneSelected(bioObs, pumeChartHandler, nestedBp, totalBioMassBtns);
            }
        });
//      Stem      
        stemChb.setOnAction(e -> {
            System.out.println(id);
            if (stemChb.isSelected()) {
                int intId = 31;
                pumeChartHandler.removeMassFromBioChart(0);
                pumeChartHandler.addMassToBioChart(maps, intId, "Stem mass", 3);
            }
            if (!stemChb.isSelected()) {
                pumeChartHandler.removeMassFromBioChart(3);
                bc.areNoneSelected(bioObs, pumeChartHandler, nestedBp, totalBioMassBtns);
            }
        });
//      Fine roots             
        fineChb.setOnAction(e -> {
            System.out.println(id);
            if (fineChb.isSelected()) {
                int intId = 25;
                pumeChartHandler.removeMassFromBioChart(0);
                pumeChartHandler.addMassToBioChart(maps, intId, "Fine root mass", 4);
            }
            if (!fineChb.isSelected()) {
                pumeChartHandler.removeMassFromBioChart(4);
                bc.areNoneSelected(bioObs, pumeChartHandler, nestedBp, totalBioMassBtns);
            }
        });
//      Coarse roots            
        coarseChb.setOnAction(e -> {
            System.out.println(id);
            if (coarseChb.isSelected()) {
                int intId = 32;
                pumeChartHandler.removeMassFromBioChart(0);
                pumeChartHandler.addMassToBioChart(maps, intId, "Coarse root mass", 5);
            }
            if (!coarseChb.isSelected()) {
                pumeChartHandler.removeMassFromBioChart(5);
                bc.areNoneSelected(bioObs, pumeChartHandler, nestedBp, totalBioMassBtns);
            }
        });

        //ActionEvent handlers 1
        treeCrownBtn.setOnAction(e -> {
            bc.removeRbtns(tscObtns, tscGp);
            bc.addOBtns(treeObs, tscGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
            bc.resetChbs(tscObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("m");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createTHCBLineChart(nestedBp);
        });
        diaBtn.setOnAction(e -> {
            bc.removeRbtns(tscObtns, tscGp);
            bc.addOBtns(treeObs, tscGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
            bc.resetChbs(tscObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("cm");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);

        });
        volBtn.setOnAction(e -> {
            bc.removeRbtns(tscObtns, tscGp);
            bc.addOBtns(treeObs, tscGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
            bc.resetChbs(tscObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("m³/ha");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);

        });
        basBtn.setOnAction(e -> {
            bc.removeRbtns(tscObtns, tscGp);
            bc.addOBtns(treeObs, tscGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("m²/ha");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);
        });
        stockBtn.setOnAction(e -> {
            bc.removeRbtns(tscObtns, tscGp);
            bc.addOBtns(treeObs, tscGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
//            bc.resetChbs(tscObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("1/ha");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);
        });
        remBtn.setOnAction(e -> {
            bc.removeRbtns(tscObtns, tscGp);
            bc.addOBtns(remObs, tscGp, 0);
//            bc.addOBtns(treeObs, tscGp, 0);

            bc.resetChbs(bioObtns);
            bc.resetChbs(tscObtns);

//          graph
//            Button button = (Button) ((Control) e.getSource());
//            pumeBtn = new PumeButton(button);
//            pumeBtn.setUnit("m³/ha/yr");
//            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
//            pumeChartHandler.removeGraph(nestedBp);
//            pumeChartHandler.createStackedBarChart(nestedBp);
//            cuttingsRb.arm();
            cuttingsRb.fire();
//            pumeChartHandler.removeFromStackedBarChart(0);

//            pumeChartHandler.createRemovalsCharts(nestedBp);
        });

        //Toggle removals radiobuttons Listener 
        remTg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (cuttingsRb.isSelected()) {
                    bc.removeRbtns(tscObtns, tscGp);
                    bc.addOBtns(remObs, tscGp, 0);
                    bc.resetChbs(bioObtns);
//                    bc.resetChbs(tscObtns);
                    //graph
                    RadioButton selectedRadioButton = (RadioButton) remTg.getSelectedToggle();
                    String toggleGroupId = selectedRadioButton.getId();
                    System.out.println("toggleGroupValue:" + toggleGroupId);
                    id.setId("37");
                    Button button = new Button("Cuttings");
                    button.setId(id.getId());
                    pumeBtn = new PumeButton(button);
                    pumeBtn.setUnit("m³/ha/yr");
                    pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
                    pumeChartHandler.removeGraph(nestedBp);
                    pumeChartHandler.createStackedBarChart(nestedBp);

                } else {
                    bc.removeRbtns(tscObtns, tscGp);
                    bc.addOBtns(remObs, tscGp, 0);
                    bc.resetChbs(bioObtns);
//                    bc.resetChbs(tscObtns);
                    //graph
                    id.setId("42");
                    Button button = new Button("Mortality");
                    button.setId(id.getId());
                    pumeBtn = new PumeButton(button);
                    pumeBtn.setUnit("m³/ha/yr");
                    pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
                    pumeChartHandler.removeGraph(nestedBp);
                    pumeChartHandler.createLineChart(nestedBp);
                }
            }
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

        //Buttons 3
        Button cbBtn = new Button("Carbon Balance");
        // id?
        // sama resultHandler case kuin wuBtn koska 0
        cbBtn.setId("48");
        rh.addIdToMap(cbBtn.getId(), 4);
        Button gppBtn = new Button("GPP");
        gppBtn.setId("44");
        rh.addIdToMap(gppBtn.getId(), 2);
        Button wuBtn = new Button("Water use");
        wuBtn.setId("48");
        rh.addIdToMap(wuBtn.getId(), 4);
        Button vgBtn = new Button("Volume growth");
        vgBtn.setId("43");
        rh.addIdToMap(vgBtn.getId(), 2);
        ObservableList<Button> cwgBtns;
        cwgBtns = FXCollections.observableArrayList();
        cwgBtns.addAll(cbBtn, gppBtn, wuBtn, vgBtn);
        bc.addBtns(cwgBtns, cwgGp);
        bc.defineBtnHandler(cwgBtns);

        ToggleGroup cbTg = new ToggleGroup();
        ObservableList<Object> cbObs;
        cbObs = FXCollections.observableArrayList();

        //Checkboxes for Buttons 3
        CheckBox ppChb = new CheckBox("Potential photosynthesis");
        ppChb.setId("6");
        CheckBox gppChb = new CheckBox("GPP");
        gppChb.setId("10");
        rh.addIdToMap(gppChb.getId(), 3);
        RadioButton nppRb = new RadioButton("NPP");
        nppRb.setId("18");
        nppRb.setToggleGroup(cbTg);
        RadioButton arRb = new RadioButton("Autotrophic respiration");
        arRb.setId("9");
        arRb.setToggleGroup(cbTg);
        cbObs.addAll(ppChb, gppChb, nppRb, arRb);

        ObservableList<Object> wuObs;
        wuObs = FXCollections.observableArrayList();

        CheckBox evapChb = new CheckBox("Evapotranspiration");
        evapChb.setId("22");
        CheckBox summerSoilChb = new CheckBox("Summer soil water");
        summerSoilChb.setId("41");
        wuObs.addAll(evapChb, summerSoilChb);

        ArrayList<ObservableList> cwgObtns;
        cwgObtns = new ArrayList<>();
        cwgObtns.add(wuObs);
        cwgObtns.add(treeObs);
        cwgObtns.add(cbObs);

        // Water use checkbox ActionEvent handlers
        evapChb.setOnAction(e -> {
            System.out.println(id);
            if (evapChb.isSelected()) {
                int intId = Integer.parseInt("22");
                pumeChartHandler.addTreeToChart(spruceMap, intId, "Evapotranspiration", 1);
            }
            if (!evapChb.isSelected() && summerSoilChb.isSelected()) {
                pumeChartHandler.removeTreeFromChart(1);
            } else {
                evapChb.setSelected(true);
            }
        });

        summerSoilChb.setOnAction(e -> {
            System.out.println(id);
            if (summerSoilChb.isSelected()) {
                int intId = Integer.parseInt("41");
                pumeChartHandler.addTreeToChart(spruceMap, intId, "Summer soil water", 2);
            }
            if (!summerSoilChb.isSelected() && evapChb.isSelected()) {
                pumeChartHandler.removeTreeFromChart(2);
            } else {
                summerSoilChb.setSelected(true);
            }
        });
        // Carbon balance checkbox ActionEvent handlers
        ppChb.setOnAction(e -> {
            System.out.println(id);
            if (ppChb.isSelected()) {
                int intId = Integer.parseInt("6");
                pumeChartHandler.addTreeToChart(spruceMap, intId, "Potential photosynthesis", 1);
            }
            if (!ppChb.isSelected() && gppChb.isSelected()) {
                pumeChartHandler.removeTreeFromChart(1);
            } else {
                ppChb.setSelected(true);
            }
        });

        gppChb.setOnAction(e -> {
            System.out.println(id);
            if (gppChb.isSelected()) {
                int intId = Integer.parseInt("10");
                pumeChartHandler.addTreeToChart(spruceMap, intId, "GPP", 2);
            }
            if (!gppChb.isSelected() && ppChb.isSelected()) {
                pumeChartHandler.removeTreeFromChart(2);
            } else {
                gppChb.setSelected(true);
            }
        });

        //Toggle Carbon balance radiobuttons Listener 
        cbTg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (nppRb.isSelected()) {
                    pumeChartHandler.removeTreeFromChart(4);
                    int intId = Integer.parseInt("18");
                    pumeChartHandler.addTreeToChart(spruceMap, intId, "NPP", 3);
                } else {
                    pumeChartHandler.removeTreeFromChart(3);
                    int intId = Integer.parseInt("9");
                    pumeChartHandler.addTreeToChart(spruceMap, intId, "Autotrophic respiration", 4);
                }
            }
        });

        //ActionEvent handlers 3
        cbBtn.setOnAction(e -> {
            bc.removeRbtns(cwgObtns, cwgGp);
            bc.addOBtns(cbObs, cwgGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
            bc.resetChbs(cwgObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("gC/m²/yr");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);
            ppChb.fire();
            pumeChartHandler.removeFromLineChart(0);

        });
        gppBtn.setOnAction(e -> {
            bc.removeRbtns(cwgObtns, cwgGp);
            bc.addOBtns(treeObs, cwgGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
            bc.resetChbs(cwgObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("gC/m²/yr");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);
        });
        wuBtn.setOnAction(e -> {
            bc.removeRbtns(cwgObtns, cwgGp);
            bc.addOBtns(wuObs, cwgGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
            bc.resetChbs(cwgObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("mm/yr");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);
            evapChb.fire();
            summerSoilChb.fire();
            pumeChartHandler.removeFromLineChart(0);
        });
        vgBtn.setOnAction(e -> {
            bc.removeRbtns(cwgObtns, cwgGp);
            bc.addOBtns(treeObs, cwgGp, 0);
//            bc.resetChbs(treeObs);
            bc.resetChbs(bioObtns);
            bc.resetChbs(cwgObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("gC/m²/yr");
            pumeChartHandler = new PumeChartHandler(pumeBtn, rh);
            pumeChartHandler.removeGraph(nestedBp);
            pumeChartHandler.createLineChart(nestedBp);
        });

        nestedBp.setTop(tscGp);

        tscBtn.setOnAction(e -> {
            if (clickedBtn != null) {
                clickedBtn.setStyle("");
            }
            Event.fireEvent(treeCrownBtn, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null));
            treeCrownBtn.fire();
            tscGp.setVisible(true);
            cwgGp.setVisible(false);
            bioGp.setVisible(false);
            nestedBp.setTop(tscGp);
        });

        bioBtn.setOnAction(e -> {
            if (clickedBtn != null) {
                clickedBtn.setStyle("");
            }
            Event.fireEvent(totalBioBtn, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null));
            totalBioBtn.fire();
            tscGp.setVisible(false);
            cwgGp.setVisible(false);
            bioGp.setVisible(true);
            nestedBp.setTop(bioGp);
        });
        cwgBtn.setOnAction(e -> {
            if (clickedBtn != null) {
                clickedBtn.setStyle("");
            }
            Event.fireEvent(cbBtn, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null));
            cbBtn.fire();
            tscGp.setVisible(false);
            bioGp.setVisible(false);
            cwgGp.setVisible(true);
            nestedBp.setTop(cwgGp);
        });

        //Basic information
        Label initSitLbl = new Label("Initial situation:");
        Label siteLbl = new Label("Site type:");
        Label weatherLbl = new Label("Weather:");
        Label managLbl = new Label("Management:");
        initStand = new InitStand();
        ComboBox initStandCb = new ComboBox(initStand.getTrees());
        initStandCb.getSelectionModel().selectFirst();
        Button initBtn = new Button("Choose file");
        initBtn.setId("initBtn");
        initBtn.setMaxSize(80, 20);
        initBtn.setDisable(true);
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
        weatherBtn.setId("weatherBtn");
        weatherBtn.setMaxSize(80, 20);
        weatherBtn.setDisable(true);

        manag = new Management();
        ComboBox thinningsCb = new ComboBox(manag.getThinnings());
        thinningsCb.getSelectionModel().selectFirst();
        Button managBtn = new Button("Choose file");
        managBtn.setId("managBtn");
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
        gp.add(initStandCb, 1, 0);
        gp.add(initBtn, 2, 0);
        gp.add(siteLbl, 0, 1);
        gp.add(siteCb, 1, 1);
        gp.add(weatherLbl, 0, 2);
        gp.add(weatherRbDef, 1, 2);
        gp.add(weatherRbCus, 2, 2);
        gp.add(weatherBtn, 3, 2);
        gp.add(managLbl, 0, 3);
//        gp.add(managRbDef, 1, 3);
        gp.add(thinningsCb, 1, 3);
//        gp.add(managRbCus, 2, 3);
        gp.add(managBtn, 2, 3);
        gp.add(yearsLbl, 0, 4);
        gp.add(yearsSpin, 1, 4);
        gp.add(runBtn, 0, 5);

        infoVb.getChildren().add(gp);
        ap.getChildren().addAll(infoVb, bp);
        AnchorPane.setLeftAnchor(infoVb, 10d);
        AnchorPane.setRightAnchor(bp, 10d);

        //Run button ActionEvent 
        runBtn.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                notifications.clear();
                info.setHeath((Heath) siteCb.getValue());
                info.setTree((Tree) initStandCb.getValue());
                info.setYears(yearsSpin.getEditor().textProperty().get());
                String weatherPath = info.getWeatherPath();
                errorHandler.checkForErrors("Weather", weatherPath, notifications);
                String managPath = info.getManagPath();
                errorHandler.checkForErrors("Management", managPath, notifications);
                String initPath = info.getInitPath();
                errorHandler.checkForErrors("Initial stand", initPath, notifications);
                Alert errorAlert = errorHandler.getErrorAlert(notifications);
                if (errorAlert == null) {

                    System.out.println("init stand: " + info.getTree());
                    System.out.println("initPath: " + info.getInitPath());
                    System.out.println("site type: " + info.getHeath());
                    System.out.println("weather path: " + info.getWeatherPath());
                    System.out.println("manag path: " + info.getManagPath());
                    System.out.println("years: " + info.getYears());
                    rh.resetConversionDone();
//                if (pumeBtn != null) {
//                    pumeBtn = new PumeButton(pumeBtn.getButton());
//                }
//                pumeChartHandler = new PumeChartHandler(pumeBtn, rh);

                    //Run model
                    try {
                        inputHandler.getRInputs(fileHandler, functions, info);
                    } catch (IOException e) {
                        System.out.println("getInputs " + e);
                    }
                    if (server == null) {
                        try {
                            server = new MyServerSocket(6011);
                        } catch (IOException e) {
                            System.out.println("Server socket " + e);
                        }
                    }

                    server.start();

                    if (pumeChartHandler != null) {
                        pumeChartHandler.removeGraph(nestedBp);
//                    bc.resetChbs(treeObs);
                        bc.resetChbs(bioObtns);
                    }

                    maps = server.getResultDataList(Integer.parseInt(info.getYears()));
                    rh.setMaps(maps);
                    pineMap = (HashMap<Integer, List<String>>) rh.getPineMap();
                    spruceMap = (HashMap<Integer, List<String>>) rh.getSpruceMap();
                    birchMap = (HashMap<Integer, List<String>>) rh.getBirchMap();
                    System.out.println("hello");
                    System.out.println("conversionDone: " + rh.isConversionDone());
                    System.out.println("initBtnFile: " + initBtnFile);
                    System.out.println("weatherBtnFile: " + weatherBtnFile);
                    System.out.println("managBtnFile: " + managBtnFile);
                } else {
                    errorAlert.showAndWait();
                }

            }
        });

        // recsCb listener
        thinningsCb.valueProperty().addListener(new ChangeListener<Thinning>() {
            @Override
            public void changed(ObservableValue ov, Thinning oldVal, Thinning newVal) {
                switch (newVal.getId()) {
                    case 0:
                        managBtn.setDisable(true);
                        managBtn.setText("Choose file");
                        info.setManagPath(defPath + "\\thinning.csv");
                        break;
                    case 1:
                        managBtn.setDisable(true);
                        managBtn.setText("Choose file");
                        // SELVITÄ OIKEA TIEDOSTO
                        info.setManagPath(defPath + "\\thinning.csv");
                        break;
                    case 2:
                        managBtn.setDisable(false);
                        fcb.setNoFileChosen(primaryStage, info, managBtn);
                        break;
                    default:
                        break;
                }
            }
        });

        // treesCb listener
        initStandCb.valueProperty().addListener(new ChangeListener<Tree>() {
            @Override
            public void changed(ObservableValue ov, Tree oldVal, Tree newVal) {
                switch (newVal.getId()) {
                    case 0:
                        initBtn.setDisable(true);
                        initBtn.setText("Choose file");
                        info.setInitPath(defPath + "\\initVar.csv");
                        break;
                    case 1:
                        initBtn.setDisable(true);
                        initBtn.setText("Choose file");
                        info.setInitPath(defPath + "\\initVar.csv");
                        break;
                    case 2:
                        initBtn.setDisable(true);
                        initBtn.setText("Choose file");
                        info.setInitPath(defPath + "\\initVar.csv");
                        break;
                    case 3:
                        initBtn.setDisable(false);
                        fcb.setNoFileChosen(primaryStage, info, initBtn);
                        break;
                    default:
                        break;
                }
            }
        });

        //Weather file chooser ActionEvent
        weatherBtn.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fcb.chooseFile(primaryStage, info, weatherBtn);
            }
        });

        //Management file chooser ActionEvent      
        managBtn.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fcb.chooseFile(primaryStage, info, managBtn);
            }
        });
        //Initial stand file chooser ActionEvent     
        initBtn.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fcb.chooseFile(primaryStage, info, initBtn);
            }
        });

        //Toggle Weather Listener    
        weatherTg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (weatherRbCus.isSelected()) {
                    weatherBtn.setDisable(false);
                    fcb.setNoFileChosen(primaryStage, info, weatherBtn);
                } else {
                    weatherBtn.setDisable(true);
                    weatherBtn.setText("Choose file");
                    info.setWeatherPath(defPath + "\\weather.csv");
                }
            }
        });

        //Key EventHandler
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

        //Spinner EventHandler      
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

        //Set Scene
        scene = new Scene(ap, 1600, 800);

        primaryStage.setTitle("PuMe 2019");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // Handle button click  
    public class ButtonHandler implements EventHandler<Event> {

        @Override
        public void handle(Event evt) {
//            id = ((Control) evt.getSource()).getId();
            id.setId(((Control) evt.getSource()).getId());
            System.out.println("rh map: " + rh.getIdMap().get(id.getId()));
            Button button = (Button) ((Control) evt.getSource());
//            String name = button.getText();
//            System.out.println("Name: " + name);
//            pumeBtn = new PumeButton(button);

            if (clickedBtn != null) {
                clickedBtn.setStyle("");
            }
            clickedBtn = button;
            clickedBtn.setStyle("-fx-background-color: burlywood");
//          split id
//            if (id != null) {
//                String[] parts = id.split(" ");
//                for (String part : parts) {
//                }
//            }

            //resultHandler
//            rh.calculate(pumeBtn);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
