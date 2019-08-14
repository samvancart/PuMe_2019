package pume2019.domain;

import com.sun.javafx.charts.Legend;
import java.awt.Color;
import static java.awt.Color.RGBtoHSB;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class PumeSeriesHandler {

    private PumeLineChart lineChart;
    private PumeStackedAreaChart stackedAreaChart;
    private PumeStackedBarChart stackedBarChart;
    private List<XYChart.Series> seriesList;
    private Map<Integer, XYChart.Series> seriesMap;

    public PumeSeriesHandler(PumeLineChart lineChart, Map<Integer, XYChart.Series> seriesMap) {
        this.lineChart = lineChart;
        this.seriesMap = seriesMap;
    }

    public PumeSeriesHandler(PumeStackedAreaChart stackedAreaChart, Map<Integer, XYChart.Series> seriesMap) {
        this.stackedAreaChart = stackedAreaChart;
        this.seriesMap = seriesMap;
    }

    public PumeSeriesHandler(PumeStackedBarChart stackedBarChart, Map<Integer, XYChart.Series> seriesMap) {
        this.stackedBarChart = stackedBarChart;
        this.seriesMap = seriesMap;
    }

    public PumeStackedAreaChart getStackedAreaChart() {
        return stackedAreaChart;
    }

    public PumeStackedBarChart getStackedBarChart() {
        return stackedBarChart;
    }
    
    public Map<Integer, XYChart.Series> getSeriesMap() {
        return seriesMap;
    }

    public PumeLineChart getLineChart() {
        return lineChart;
    }

    public void setLineChart(PumeLineChart lineChart) {
        this.lineChart = lineChart;
    }

    public void setSeriesList(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    public List<XYChart.Series> getSeriesList() {
        return seriesList;
    }

    public void setSeriesMap(Map<Integer, XYChart.Series> seriesMap) {
        this.seriesMap = seriesMap;
    }

    public void addSeriesToLineChart(Integer key, XYChart.Series series) {
//        seriesMap.clear();
        seriesMap.putIfAbsent(key, series);
        lineChart.getLineChart().getData().add(series);
    }

    public void addSeriesToStackedAreaChart(Integer key, XYChart.Series series) {
        seriesMap.putIfAbsent(key, series);
        stackedAreaChart.getStackedAreaChart().getData().add(series);
    }

    public void addSeriesToStackedBarChart(Integer key, XYChart.Series series) {
        seriesMap.putIfAbsent(key, series);
        stackedBarChart.getStackedBarChart().getData().add(series);
    }

    public void removeSeriesFromLineChart(Integer key) {
//        seriesMap.clear();
//        seriesMap.remove(key);
//        seriesMap.put(key, null);
        ObservableList<Series<Integer, Double>> newSeriesList = FXCollections.observableArrayList();
        seriesMap.replace(key, null);
        for (Map.Entry<Integer, Series> entry : seriesMap.entrySet()) {
            if (seriesMap.get(entry.getKey()) != null) {
                newSeriesList.add(entry.getValue());
            }
        }
        lineChart.getLineChart().setData(newSeriesList);
    }

    public void removeSeriesFromStackedAreaChart(Integer key) {
        ObservableList<Series<Integer, Double>> newSeriesList = FXCollections.observableArrayList();
        seriesMap.replace(key, null);
        for (Map.Entry<Integer, Series> entry : seriesMap.entrySet()) {
            if (seriesMap.get(entry.getKey()) != null) {
                newSeriesList.add(entry.getValue());
            }
        }
        stackedAreaChart.getStackedAreaChart().setData(newSeriesList);
    }

    public void removeSeriesFromStackedBarChart(Integer key) {
        ObservableList<Series<String, Double>> newSeriesList = FXCollections.observableArrayList();
        seriesMap.replace(key, null);
        for (Map.Entry<Integer, Series> entry : seriesMap.entrySet()) {
            if (seriesMap.get(entry.getKey()) != null) {
                newSeriesList.add(entry.getValue());
            }
        }
        stackedBarChart.getStackedBarChart().setData(newSeriesList);
    }

    public void colourSeries(LineChart<Integer, Double> chart, Series series, String name) {
        name = name.toLowerCase();
        Color color = Color.RED;
        if (name.equals("pine")) {
            color = Color.CYAN;
        } else if (name.equals("spruce")) {
            color = Color.BLUE;
        } else if (name.equals("birch")) {
            color = Color.GREEN;
        } else if (name.equals("crown base")) {
            color = Color.BLACK;
        } else if (name.equals("48")) {
            color = Color.RED;
        } else if (name.equals("pine2")) {
            color = Color.lightGray;
        } else if (name.equals("spruce2")) {
            color = Color.MAGENTA;
        } else if (name.equals("birch2")) {
            color = Color.YELLOW;
        }

        String rgb = String.format("%d, %d, %d",
                (int) (color.getRed() * 1),
                (int) (color.getGreen() * 1),
                (int) (color.getBlue() * 1));
        System.out.println("RGB: "+rgb);

        for (int index = 0; index < series.getData().size(); index++) {
            // we're looping for each data point, changing the color of line symbol
            XYChart.Data dataPoint = (XYChart.Data) series.getData().get(index);
            Node lineSymbol = dataPoint.getNode().lookup(".chart-line-symbol");
            lineSymbol.setStyle("-fx-background-color: rgba(" + rgb + ", 1.0), whitesmoke;");
        }
        series.getNode().setStyle("-fx-border-style: solid; -fx-stroke:rgba(" + rgb + ", 1.0); -fx-background-color: rgba(" + rgb + ", 1.0);");
        this.colourLegend(chart, series, rgb);
    }

    public void colourLegend(LineChart<Integer, Double> chart, Series series, String rgb) {
        for (Node n : chart.getChildrenUnmodifiable()) {
            if (n instanceof Legend) {
                for (Legend.LegendItem legendItem : ((Legend) n).getItems()) {
                    if (legendItem.getText() != null && series.getName() != null) {
                        if (legendItem.getText().equals(series.getName())) {
                            Node itemSymbol = (Node) legendItem.getSymbol().lookup(".chart-legend-item-symbol");
                            legendItem.getSymbol().setStyle("-fx-background-color: rgba(" + rgb + ", 1.0), white;");
                            itemSymbol.setStyle("-fx-background-color: rgba(" + rgb + ", 1.0), white;");
                        }
                    }
                }
            }
        }
    }
}
