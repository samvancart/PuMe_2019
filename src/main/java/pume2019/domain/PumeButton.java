package pume2019.domain;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;

public class PumeButton {

    private Button button;
    private List<List<String>> dataLists;
    private String unit;

    public PumeButton(Button button) {
        this.button = button;
        dataLists = new ArrayList<>();
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void setDataLists(List<List<String>> dataLists) {
        this.dataLists = dataLists;
    }

    public Button getButton() {
        return button;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
    
    public List<List<String>> getDataLists() {
        return dataLists;
    }

    public void addToList(List<String> data) {
        dataLists.add(data);
    }

    public List<String> getDataList(int index) {
        List<String> data = dataLists.get(index);
        return data;
    }

}
