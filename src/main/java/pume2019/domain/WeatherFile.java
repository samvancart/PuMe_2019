package pume2019.domain;

public class WeatherFile {

    private String name;
    private int id;

    public WeatherFile(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
