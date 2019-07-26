package pume2019.controllers;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import static javafx.scene.input.KeyEvent.KEY_RELEASED;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
                CheckBox chb = (CheckBox) objectList.get(j);
                chb.setSelected(false);
            }
        }
    }

    public void defineBtnHandler(ObservableList<Button> btns) {
        btns.forEach(btn -> btn.addEventHandler(MouseEvent.MOUSE_CLICKED, bh));
        btns.forEach(btn -> btn.addEventHandler(KEY_RELEASED, bh));
    }
}
