
package pume2019.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Management {
    
        private ObservableList<Recommendation> recs;

    public Management() {
        recs = FXCollections.observableArrayList();
        recs.add(new Recommendation("Standard recommendations", 0));
        recs.add(new Recommendation("Unmanaged", 1));
        recs.add(new Recommendation("Custom .csv", 2));
    }


    
    public ObservableList<Recommendation> getRecs() {
        return recs;
    }
}
