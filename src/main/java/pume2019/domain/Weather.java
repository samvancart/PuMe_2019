package pume2019.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Weather {

    private ObservableList<WeatherFile> weather;

    public Weather() {
        weather = FXCollections.observableArrayList();
        weather.add(new WeatherFile("Default .csv", 0));
        weather.add(new WeatherFile("Custom .csv", 1));
    }

    public ObservableList<WeatherFile> getWeather() {
        return weather;
    }
}
