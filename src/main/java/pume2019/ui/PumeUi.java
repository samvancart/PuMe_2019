package pume2019.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
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
import pume2019.domain.Notifications;
import pume2019.domain.PumeButton;
import pume2019.domain.PumeId;
import pume2019.domain.Thinning;
import pume2019.domain.SiteInfo;
import pume2019.domain.Tree;
import pume2019.domain.Weather;
import pume2019.domain.WeatherFile;

public class PumeUi extends Application {

    private Scene scene;
    private Info info;
    private InitStand initStand;
    private SiteInfo siteInfo;
    private Management manag;
    private Weather weather;
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
    private PumeId id = new PumeId();
    private PumeErrorHandler errorHandler = new PumeErrorHandler();
    private FileChooserButton fcb = new FileChooserButton();
    private Notifications notifications = new Notifications();
    private int oldYears;
    private String styles;
    private String greyFillStyle;
    private String clickedBtnStyle;

    @Override
    public void init() throws IOException, URISyntaxException {
        // config props
        Properties properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(is);

        String inputFile = properties.getProperty("inputFile");
        String pathCsv = properties.getProperty("pathCsv");
        styles = properties.getProperty("styles");

        fileHandler = new RFileHandler(inputFile);
        bc = new ButtonController(new ButtonHandler());
        String jarDir = new File(RFunctions.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI()).getPath();

        defPath = pathCsv;
        info = new Info();
        info.setWeatherPath(defPath + "\\weather.csv");
        info.setManagPath(defPath + "\\thinning.csv");
        info.setInitPath(defPath + "\\initVar.csv");
        info.setYears("100");
        oldYears = Integer.parseInt(info.getYears());

        functions = new RFunctions();
        inputHandler = new RInputHandler();
        rh = new ResultHandler();

        greyFillStyle = "-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1;"
                + "-fx-border-insets: 1;"
                + "-fx-border-radius: 1;"
                + "-fx-border-color: black;"
                + "-fx-background-color:linear-gradient(#F4F4F4,#C3C3C2);";

        clickedBtnStyle = "-fx-border-width: 2;"
                + "-fx-border-color:black;";

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
        ap.setStyle("-fx-background-color:whitesmoke;");
        VBox infoVb = new VBox();
        infoVb.setStyle(greyFillStyle);
        infoVb.prefHeightProperty().bind(ap.heightProperty());

        GridPane gp = new GridPane();
        gp.setHgap(20d);
        gp.setVgap(80d);
        gp.setPadding(new Insets(10d, 10d, 10d, 10d));

        BorderPane bp = new BorderPane();

        bp.prefHeightProperty().bind(ap.heightProperty());
        bp.prefWidthProperty().bind(ap.widthProperty().subtract(infoVb.widthProperty().add(20d)));

        BorderPane rightBp = new BorderPane();
        rightBp.prefHeightProperty().bind(ap.heightProperty());
        rightBp.prefWidthProperty().bind(ap.widthProperty().subtract(infoVb.widthProperty().add(20d)));
        rightBp.setStyle(greyFillStyle);
        Label startLbl = new Label("Prebas App");
        startLbl.setStyle("-fx-font-size:86;");
        startLbl.setOpacity(0.2);
        rightBp.setCenter(startLbl);

        VBox bpLeftVb = new VBox();

        bpLeftVb.setStyle(greyFillStyle);
        bp.setLeft(bpLeftVb);
        bpLeftVb.setPadding(new Insets(5, 5, 5, 5));
        bpLeftVb.setSpacing(20);

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

        nestedBp.setStyle(greyFillStyle);
        bp.setCenter(nestedBp);

        GridPane tscGp = new GridPane();
        tscGp.setHgap(10);
        tscGp.setVgap(10);

        tscGp.setStyle(greyFillStyle);

        //Buttons 1
        Button treeCrownBtn = new Button("Tree Height, Crown base");
        treeCrownBtn.setId("11");
// 5=weighted average
        rh.addIdToMap("11", 5);
        rh.addIdToMap("14", 5);
        Button diaBtn = new Button("Diameter");
        diaBtn.setId("12");
        rh.addIdToMap(diaBtn.getId(), 5);
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
            int intId = Integer.parseInt(id.getId());
            if (pineChb.isSelected()) {
                if (intId == 37) {
                    pumeChartHandler.removeFromStackedBarChart(0);
                }
                pumeChartHandler.addTreeToChart(pumeBtn, maps, pineMap, intId, "Pine", 1);
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
            int intId = Integer.parseInt(id.getId());
            if (spruceChb.isSelected()) {
                if (intId == 37) {
                    pumeChartHandler.removeFromStackedBarChart(0);
                }
                pumeChartHandler.addTreeToChart(pumeBtn, maps, spruceMap, intId, "Spruce", 2);
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
            int intId = Integer.parseInt(id.getId());
            if (birchChb.isSelected()) {
                if (intId == 37) {
                    pumeChartHandler.removeFromStackedBarChart(0);
                }
                pumeChartHandler.addTreeToChart(pumeBtn, maps, birchMap, intId, "Birch", 3);
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
        bioGp.setStyle(greyFillStyle);

        //Buttons 2
        Button totalBioBtn = new Button("Total biomass");
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
            bc.resetChbs(bioObtns);
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
            bc.resetChbs(bioObtns);
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
            bc.resetChbs(bioObtns);
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
            bc.resetChbs(bioObtns);
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
            bc.resetChbs(bioObtns);
            bc.resetChbs(tscObtns);
            cuttingsRb.fire();
        });

        //Toggle removals radiobuttons Listener 
        remTg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (cuttingsRb.isSelected()) {
                    bc.removeRbtns(tscObtns, tscGp);
                    bc.addOBtns(remObs, tscGp, 0);
                    bc.resetChbs(bioObtns);
                    //graph
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
        cwgGp.setStyle(greyFillStyle);
        //Buttons 3
        Button cbBtn = new Button("Carbon Balance");
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
            if (evapChb.isSelected()) {
                int intId = Integer.parseInt("22");
                pumeChartHandler.addTreeToChart(pumeBtn, maps, info.getInitMap(), intId, "Evapotranspiration", 1);
            }
            if (!evapChb.isSelected() && summerSoilChb.isSelected()) {
                pumeChartHandler.removeTreeFromChart(1);
            } else {
                evapChb.setSelected(true);
            }
        });

        summerSoilChb.setOnAction(e -> {
            if (summerSoilChb.isSelected()) {
                int intId = Integer.parseInt("41");
                pumeChartHandler.addTreeToChart(pumeBtn, maps, info.getInitMap(), intId, "Summer soil water", 2);
            }
            if (!summerSoilChb.isSelected() && evapChb.isSelected()) {
                pumeChartHandler.removeTreeFromChart(2);
            } else {
                summerSoilChb.setSelected(true);
            }
        });
        // Carbon balance checkbox ActionEvent handlers
        ppChb.setOnAction(e -> {
            if (ppChb.isSelected()) {
                int intId = Integer.parseInt("6");
                pumeChartHandler.addTreeToChart(pumeBtn, maps, info.getInitMap(), intId, "Potential photosynthesis", 1);
            }
            if (!ppChb.isSelected() && gppChb.isSelected()) {
                pumeChartHandler.removeTreeFromChart(1);
            } else {
                ppChb.setSelected(true);
            }
        });

        gppChb.setOnAction(e -> {
            if (gppChb.isSelected()) {
                int intId = Integer.parseInt("10");
                pumeChartHandler.addTreeToChart(pumeBtn, maps, info.getInitMap(), intId, "GPP", 2);
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
                    pumeChartHandler.addTreeToChart(pumeBtn, maps, info.getInitMap(), intId, "NPP", 3);
                } else if (arRb.isSelected()) {
                    pumeChartHandler.removeTreeFromChart(3);
                    int intId = Integer.parseInt("9");
                    pumeChartHandler.addTreeToChart(pumeBtn, maps, info.getInitMap(), intId, "Autotrophic respiration", 4);
                }
            }
        });

        //ActionEvent handlers 3
        cbBtn.setOnAction(e -> {
            bc.removeRbtns(cwgObtns, cwgGp);
            bc.addOBtns(cbObs, cwgGp, 0);
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
            bc.resetChbs(bioObtns);
            bc.resetChbs(cwgObtns);
//          graph
            Button button = (Button) ((Control) e.getSource());
            pumeBtn = new PumeButton(button);
            pumeBtn.setUnit("m³/yr");
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
            bioBtn.setStyle("");
            cwgBtn.setStyle("");
            tscBtn.setStyle(clickedBtnStyle);
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
            tscBtn.setStyle("");
            cwgBtn.setStyle("");
            bioBtn.setStyle(clickedBtnStyle);
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
            tscBtn.setStyle("");
            bioBtn.setStyle("");
            cwgBtn.setStyle(clickedBtnStyle);
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
        Tooltip initTool = new Tooltip("Choose file");
        initTool.setStyle("-fx-font-size: 15");
        initBtn.setId("initBtn");
        initBtn.setTooltip(initTool);
        initBtn.setMaxSize(80, 20);
        initBtn.setDisable(true);
        siteInfo = new SiteInfo();
        ComboBox siteCb = new ComboBox(siteInfo.getHeaths());
        siteCb.getSelectionModel().selectFirst();

        weather = new Weather();
        ComboBox weatherCb = new ComboBox(weather.getWeather());
        weatherCb.getSelectionModel().selectFirst();

        Button weatherBtn = new Button("Choose file");
        Tooltip weatherTool = new Tooltip("Choose file");
        weatherTool.setStyle("-fx-font-size: 15");
        weatherBtn.setId("weatherBtn");
        weatherBtn.setTooltip(weatherTool);
        weatherBtn.setMaxSize(80, 20);
        weatherBtn.setDisable(true);

        manag = new Management();
        ComboBox thinningsCb = new ComboBox(manag.getThinnings());
        thinningsCb.getSelectionModel().selectFirst();
        Button managBtn = new Button("Choose file");
        Tooltip managTool = new Tooltip("Choose file");
        managTool.setStyle("-fx-font-size: 15");
        managBtn.setId("managBtn");
        managBtn.setTooltip(managTool);
        managBtn.setMaxSize(80, 20);
        managBtn.setDisable(true);
        Label yearsLbl = new Label("Years:");

        ObservableList<Object> labels;
        labels = FXCollections.observableArrayList();
        labels.addAll(initSitLbl, siteLbl, weatherLbl, managLbl, yearsLbl);
        bc.setFontToLabels(labels);

        final Spinner yearsSpin = new Spinner();
        String INITIAL_VALUE = "100";
        IntegerSpinnerValueFactory yearsSpinFactory = new IntegerSpinnerValueFactory(1, 200, Integer.parseInt(INITIAL_VALUE));
        yearsSpin.setValueFactory(yearsSpinFactory);
        yearsSpin.setEditable(true);
        yearsSpin.setPrefSize(70, 20);

        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setTitle("Information Dialog");
        infoAlert.setHeaderText("An Information Dialog");
        infoAlert.setContentText("Information Message");
        Label yearsSpinLbl = new Label();
        ImageView infoIcon = new ImageView(this.getClass().getResource("/com/sun/javafx/scene/control/skin/modena/dialog-information.png").toString());
        infoIcon.setFitHeight(40);
        infoIcon.setFitWidth(40);
        yearsSpinLbl.setGraphic(infoIcon);
        Tooltip infoTool = new Tooltip("Maximum value for default weather.csv is 129 years. Maximum value for custom file is 200 years.");
        infoTool.setStyle("-fx-font-size: 15");
        yearsSpinLbl.setTooltip(infoTool);

        Button runBtn = new Button("Run Model");

        gp.add(initSitLbl, 0, 0);
        gp.add(initStandCb, 1, 0);
        gp.add(initBtn, 2, 0);
        gp.add(siteLbl, 0, 1);
        gp.add(siteCb, 1, 1);
        gp.add(weatherLbl, 0, 2);
        gp.add(weatherCb, 1, 2);
        gp.add(weatherBtn, 2, 2);
        gp.add(managLbl, 0, 3);
        gp.add(thinningsCb, 1, 3);
        gp.add(managBtn, 2, 3);
        gp.add(yearsLbl, 0, 4);
        gp.add(yearsSpin, 1, 4);
        gp.add(yearsSpinLbl, 2, 4);
        gp.add(runBtn, 0, 5);

        GridPane.setHgrow(gp, Priority.ALWAYS);
        GridPane.setVgrow(gp, Priority.ALWAYS);

        infoVb.getChildren().add(gp);
        ap.getChildren().add(infoVb);
        ap.getChildren().add(rightBp);
        AnchorPane.setLeftAnchor(infoVb, 10d);
        AnchorPane.setRightAnchor(bp, 10d);
        AnchorPane.setRightAnchor(rightBp, 10d);

        //Run button ActionEvent 
        runBtn.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                notifications.clear();
                info.setHeath((Heath) siteCb.getValue());
                info.setTree((Tree) initStandCb.getValue());
                info.setSpinnerValue(yearsSpinFactory, yearsSpin);

                info.setThinning((Thinning) thinningsCb.getValue());
                String weatherPath = info.getWeatherPath();
                errorHandler.checkForFileErrors("Weather", weatherPath, notifications);
                String managPath = info.getManagPath();
                errorHandler.checkForFileErrors("Management", managPath, notifications);
                String initPath = info.getInitPath();
                errorHandler.checkForFileErrors("Initial situation", initPath, notifications);
                Alert errorAlert = errorHandler.getErrorAlert(notifications);

                if (errorAlert == null) {
                    rh.resetConversionDone();

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
                    if (!server.isBindError()) {

                        try {
                            server.start();

                        } catch (InterruptedException | URISyntaxException | IOException ex) {
                            Logger.getLogger(PumeUi.class.getName()).log(Level.SEVERE, null, ex);

                        }
                                                                                      
                        errorHandler.checkForRErrors(server.getResultData(), notifications);
                        errorAlert = errorHandler.getErrorAlert(notifications);
                        if (errorAlert == null) {
                            if (pumeChartHandler != null) {
                                pumeChartHandler.removeGraph(nestedBp);
                                bc.resetChbs(bioObtns);
                            }

                            maps = server.getResultDataList(Integer.parseInt(info.getYears()));
                            rh.setMaps(maps);
                            rh.convertToGrams(maps.get(0).get(44), maps.get(1).get(44), maps.get(2).get(44), 44);
                            rh.setConvertedVals(maps, 44, 10);
                            rh.setConvertedVals(maps, 9, 9);
                            rh.setConvertedVals(maps, 18, 18);
                            pineMap = (HashMap<Integer, List<String>>) rh.getPineMap();
                            spruceMap = (HashMap<Integer, List<String>>) rh.getSpruceMap();
                            birchMap = (HashMap<Integer, List<String>>) rh.getBirchMap();
                            info.setInitMap(maps, info.getTree().getId());
                            ap.getChildren().remove(rightBp);
                            ap.getChildren().remove(bp);
                            ap.getChildren().add(bp);
                            if (clickedBtn != null) {
                                clickedBtn.fire();
                            } else {
                                tscBtn.fire();
                            }
                            // Old years
                            oldYears = Integer.parseInt(info.getYears());

                        } else {
                            errorAlert.showAndWait();
                        }
                    } else {
                        errorHandler.serverSocketBindError(server.getBindErrorMsg(), notifications);
                        errorAlert = errorHandler.getErrorAlert(notifications);
                        errorAlert.showAndWait();
                    }

                } else {
                    errorAlert.showAndWait();
                }
                info.setYears(String.valueOf(oldYears));
                yearsSpin.getEditor().setText(info.getYears());
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
                        managTool.setText(managBtn.getText());
                        managBtn.setTooltip(managTool);
                        info.setManagPath(defPath + "\\thinning.csv");
                        break;
                    case 1:
                        managBtn.setDisable(true);
                        managBtn.setText("Choose file");
                        managTool.setText(managBtn.getText());
                        managBtn.setTooltip(managTool);
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
                        initTool.setText("Choose file");
                        initBtn.setTooltip(initTool);
                        info.setInitPath(defPath + "\\initVar.csv");
//                        runBtn.fire();
                        break;
                    case 1:
                        initBtn.setDisable(true);
                        initBtn.setText("Choose file");
                        initTool.setText("Choose file");
                        initBtn.setTooltip(initTool);
                        info.setInitPath(defPath + "\\initVarYoungPine.csv");
//                        runBtn.fire();
                        break;
                    case 2:
                        initBtn.setDisable(true);
                        initBtn.setText("Choose file");
                        initTool.setText("Choose file");
                        initBtn.setTooltip(initTool);
                        info.setInitPath(defPath + "\\initVarYoungSpruce.csv");
//                        runBtn.fire();
                        break;
                    case 3:
                        initBtn.setDisable(false);
                        fcb.setNoFileChosen(primaryStage, info, initBtn);
                        break;
                    default:
                        break;
                }
//                System.out.println("init: " + info.getInitPath());
//                runBtn.fire();

            }
        });

        // weatherCb listener
        weatherCb.valueProperty().addListener(new ChangeListener<WeatherFile>() {
            @Override
            public void changed(ObservableValue ov, WeatherFile oldVal, WeatherFile newVal) {
                switch (newVal.getId()) {
                    case 0:
                        weatherBtn.setDisable(true);
                        weatherBtn.setText("Choose file");
                        weatherTool.setText(weatherBtn.getText());
                        weatherBtn.setTooltip(weatherTool);
                        info.setWeatherPath(defPath + "\\weather.csv");
                        break;
                    case 1:
                        weatherBtn.setDisable(false);
                        fcb.setNoFileChosen(primaryStage, info, weatherBtn);
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
                if (info.getWeatherPath() == null) {
                    weatherTool.setText(weatherBtn.getText());
                    weatherBtn.setTooltip(weatherTool);
                } else {
                    weatherTool.setText(info.getWeatherPath());
                    weatherBtn.setTooltip(weatherTool);
                    runBtn.fire();
                }

            }
        });

        //Management file chooser ActionEvent      
        managBtn.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fcb.chooseFile(primaryStage, info, managBtn);
                if (info.getManagPath() == null) {
                    managTool.setText(managBtn.getText());
                    managBtn.setTooltip(managTool);
                } else {
                    managTool.setText(info.getManagPath());
                    managBtn.setTooltip(managTool);
                    runBtn.fire();
                }
            }
        });
        //Initial stand file chooser ActionEvent     
        initBtn.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fcb.chooseFile(primaryStage, info, initBtn);
                if (info.getInitPath() == null) {
                    initTool.setText(initBtn.getText());
                    initBtn.setTooltip(initTool);
                } else {
                    initTool.setText(info.getInitPath());
                    initBtn.setTooltip(initTool);
                    runBtn.fire();
                }

            }
        });

        //Spinner enterKey EventHandler
        EventHandler<KeyEvent> spinnerEnterKeyEventHandler;
        spinnerEnterKeyEventHandler = new EventHandler<KeyEvent>() {
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
        //RunBtn enterKey EventHandler
        EventHandler<KeyEvent> buttonEnterKeyEventHandler;
        buttonEnterKeyEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    Button button = (Button) ((Control) event.getSource());
                    try {
                        button.fire();
                    } catch (Exception e) {
                    }
                }
            }
        };
        runBtn.addEventHandler(KeyEvent.KEY_PRESSED, buttonEnterKeyEventHandler);
        tscBtn.addEventHandler(KeyEvent.KEY_PRESSED, buttonEnterKeyEventHandler);
        bioBtn.addEventHandler(KeyEvent.KEY_PRESSED, buttonEnterKeyEventHandler);
        cwgBtn.addEventHandler(KeyEvent.KEY_PRESSED, buttonEnterKeyEventHandler);

        //Spinner EventHandler      
        yearsSpin.getEditor().addEventHandler(KeyEvent.KEY_PRESSED, spinnerEnterKeyEventHandler);

        yearsSpin.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!yearsSpin.getEditor().textProperty().get().matches("[0-9]*")) {
                if (!oldValue.matches("[0-9]*")) {
                    yearsSpin.getEditor().textProperty().set(INITIAL_VALUE);
                }
                Platform.runLater(() -> {
//                    yearsSpin.getEditor().textProperty().set("100");
                    info.setYears(String.valueOf(oldYears));
                    yearsSpin.getEditor().setText(info.getYears());
                });
                infoVb.requestFocus();
            }
        });

        //Set Scene
        scene = new Scene(ap, 1600, 800);
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        ap.setPrefSize(bounds.getWidth() / 2, bounds.getHeight() / 2);
        primaryStage.setMinWidth(scene.getWidth() + 35d);
        primaryStage.setMinHeight(scene.getHeight() + 35d);
        primaryStage.setMaxWidth(bounds.getWidth());
        primaryStage.setMaxHeight(bounds.getHeight());

        primaryStage.setX((bounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((bounds.getHeight() - primaryStage.getHeight()) / 4);

        scene.getStylesheets().add(styles);
        primaryStage.setTitle("Prebas App");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // Handle button click  
    public class ButtonHandler implements EventHandler<Event> {

        @Override
        public void handle(Event evt) {
            id.setId(((Control) evt.getSource()).getId());
            Button button = (Button) ((Control) evt.getSource());

            if (clickedBtn != null) {
                clickedBtn.setStyle("");
            }
            clickedBtn = button;
            clickedBtn.setStyle(clickedBtnStyle);
            button.fire();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
