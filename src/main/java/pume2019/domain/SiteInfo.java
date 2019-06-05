package pume2019.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SiteInfo {

    private ObservableList<Heath> heaths;

    public SiteInfo() {
        heaths = FXCollections.observableArrayList();
        heaths.add(new Heath("Herb-rich Heath", 0));
        heaths.add(new Heath("Mesic Heath", 1));
        heaths.add(new Heath("Sub-xeric Heath", 2));
        heaths.add(new Heath("Xeric Heath", 3));
        heaths.add(new Heath("Barren Heath", 4));
    }

    public ObservableList<Heath> getHeaths() {
        return heaths;
    }

}
