package pume2019.controllers;

import com.sun.javafx.collections.ObservableListWrapper;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import static javafx.scene.input.KeyEvent.KEY_RELEASED;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import pume2019.dataHandler.PumeChartHandler;
import pume2019.ui.PumeUi.ButtonHandler;

public class ButtonController {

    private final ButtonHandler bh;

    public ButtonController(ButtonHandler bh) {
        this.bh = bh;
    }

    public void addBtns(ObservableList<Button> btns, GridPane gp) {
        for (int i = 0; i < btns.size(); i++) {
            Button btn = btns.get(i);
            btn.prefWidthProperty().bind(gp.widthProperty().divide(btns.size()));
            Tooltip t = new Tooltip(btn.getText());
            t.setStyle("-fx-font-size: 15");
            btn.setTooltip(t);
            gp.add(btn, i, 0);
        }
    }
    


    public void addOBtns(ObservableList<Object> oBtns, GridPane gp, int startIndex) {
        for (int i = 0; i < oBtns.size(); i++) {
            CheckBox chB;
            RadioButton rb;
            if (oBtns.get(i).getClass().equals(CheckBox.class)) {
                chB = (CheckBox) oBtns.get(i);
                chB.prefWidthProperty().bind(gp.widthProperty().divide(oBtns.size()));
                gp.add(chB, i + startIndex, 1, 2, 2);
            } else {
                rb = (RadioButton) oBtns.get(i);
                rb.prefWidthProperty().bind(gp.widthProperty().divide(oBtns.size()));
                gp.add(rb, i + startIndex, 1, 2, 2);
            }
        }
    }

    public void removeOBtns(ObservableList<Object> oBtns, GridPane gp) {
        for (int i = 0; i < oBtns.size(); i++) {
            CheckBox chB = (CheckBox) oBtns.get(i);
            gp.getChildren().remove(chB);
        }
    }
    

    public void removeRbtns(ArrayList<ObservableList> rBtns, GridPane gp) {
        for (int i = 0; i < rBtns.size(); i++) {
            ObservableList btns = rBtns.get(i);
            gp.getChildren().removeAll(btns);
        }
    }

//    public void resetChbs(ObservableList<Object> chbs) {      
//        chbs.stream().map((o) -> (CheckBox) o).forEachOrdered((chb) -> {
//            chb.setSelected(false);
//        });
//    }
    public void resetChbs(ArrayList<ObservableList> chbLists) {
        for (int i = 0; i < chbLists.size(); i++) {
            ObservableList<Object> objectList = chbLists.get(i);
            for (int j = 0; j < objectList.size(); j++) {
                if (objectList.get(j).getClass().equals((CheckBox.class))) {
                    CheckBox chb = (CheckBox) objectList.get(j);
                    chb.setSelected(false);
                } else {
                    RadioButton rb = (RadioButton) objectList.get(j);
                    rb.setSelected(false);
                }

            }
        }
    }

    public void defineBtnHandler(ObservableList<Button> btns) {
        btns.forEach(btn -> btn.addEventHandler(MouseEvent.MOUSE_CLICKED, bh));
        btns.forEach(btn -> btn.addEventHandler(KEY_RELEASED, bh));
    }

    public void areNoneSelected(ObservableList<Object> buttons, PumeChartHandler handler, BorderPane nestedBp, List<Button> totalBioMassBtns) {
        boolean noneSelected = true;
        for (int i = 0; i < buttons.size(); i++) {
            CheckBox c = (CheckBox) buttons.get(i);
            if (c.isSelected()) {
                noneSelected = false;
            }
        }
        if (noneSelected) {
            handler.createStackedAreaChartOfBiomass(nestedBp, totalBioMassBtns);
        }
    }

    public void areNoneSelectedRemovals(ObservableList<Object> buttons, PumeChartHandler handler, BorderPane nestedBp) {
        boolean noneSelected = true;
        for (int i = 0; i < buttons.size(); i++) {
            CheckBox c = (CheckBox) buttons.get(i);
            if (c.isSelected()) {
                noneSelected = false;
            }
        }
        if (noneSelected) {
            handler.createStackedBarChart(nestedBp);
        }
    }

    public void setFontToLabels(ObservableList<Object> labels) {
        for (int i = 0; i < labels.size(); i++) {
            Label label = (Label) labels.get(i);
            label.setStyle("-fx-font-size: 1.3em");
        }
    }
}
