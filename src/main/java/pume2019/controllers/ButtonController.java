
package pume2019.controllers;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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
            btns.forEach(btn -> btn.addEventHandler(MouseEvent.MOUSE_CLICKED, bh));
        }
}
