package pume2019.domain;

public class Recommendation {

    private String name;
    private int id;

    public Recommendation(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

}
