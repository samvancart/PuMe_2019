
package pume2019.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Management {
    
        private ObservableList<Thinning> thinnings;

    public Management() {
        thinnings = FXCollections.observableArrayList();
        thinnings.add(new Thinning("Standard recommendations", 0));
        thinnings.add(new Thinning("Unmanaged", 1));
        thinnings.add(new Thinning("Custom .csv", 2));
    }


    
    public ObservableList<Thinning> getThinnings() {
        return thinnings;
    }
}
