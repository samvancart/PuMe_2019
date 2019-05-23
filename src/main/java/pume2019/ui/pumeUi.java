package pume2019.ui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class pumeUi extends Application {

    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane ap = new AnchorPane();
        VBox vb = new VBox();
        vb.setStyle("-fx-padding: 10;" + 
                      "-fx-border-style: solid inside;" + 
                      "-fx-border-width: 2;" +
                      "-fx-border-insets: 5;" + 
                      "-fx-border-radius: 5;" + 
                      "-fx-border-color: black;");
        GridPane gp = new GridPane();
        gp.setHgap(40);
        gp.setVgap(40);
        gp.setPadding(new Insets(10,10,10,10));
        Label siteLbl = new Label("Site information:");
        Label initLbl = new Label("Initial state:");
        Label obsLbl = new Label("Observed data:");
        ComboBox treesCb = new ComboBox();
        ComboBox initCb = new ComboBox();
        ComboBox obsCb = new ComboBox();
        Button button = new Button("Button");
        gp.add(siteLbl, 0, 0);
        gp.add(treesCb, 1, 0);
        gp.add(initLbl, 0, 1);
        gp.add(initCb, 1, 1);
        gp.add(obsLbl, 0, 2);
        gp.add(obsCb, 1, 2);
        
        vb.getChildren().add(gp);
        ap.getChildren().add(vb);
        AnchorPane.setLeftAnchor(vb, 10d);

        scene = new Scene(ap, 1200, 800);

        primaryStage.setTitle("PuMe 2019");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
