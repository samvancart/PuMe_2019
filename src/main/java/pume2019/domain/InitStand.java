package pume2019.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InitStand {

    private ObservableList<Tree> trees;

    public InitStand() {
        trees = FXCollections.observableArrayList();
        trees.add(new Tree("Mixed Stand", 0));
        trees.add(new Tree("Young Pine", 1));
        trees.add(new Tree("Young Spruce", 2));
        trees.add(new Tree("Young Birch", 3));
        trees.add(new Tree("Custom", 4));
    }

    public ObservableList<Tree> getTrees() {
        return trees;
    }

}
