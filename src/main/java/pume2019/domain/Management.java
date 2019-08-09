
package pume2019.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Management {
    
        private ObservableList<Thinning> thinnings;

    public Management() {
        thinnings = FXCollections.observableArrayList();
        thinnings.add(new Thinning("Standard recommendations", 0,"NA",1,1));
        thinnings.add(new Thinning("Unmanaged", 1,"NA",0,0));
        thinnings.add(new Thinning("Custom .csv", 2,"thinning",0,0));
    }


    
    public ObservableList<Thinning> getThinnings() {
        return thinnings;
    }
}
