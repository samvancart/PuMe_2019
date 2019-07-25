package pume2019.domain;

import com.sun.javafx.charts.Legend;
import java.awt.Color;
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
    private List<XYChart.Series> seriesList;
    private Map<Integer, XYChart.Series> seriesMap;

    public PumeSeriesHandler(PumeLineChart lineChart, List<XYChart.Series> seriesList) {
        this.lineChart = lineChart;
        this.seriesList = seriesList;
    }

    public PumeSeriesHandler(PumeLineChart lineChart, Map<Integer, XYChart.Series> seriesMap) {
        this.lineChart = lineChart;
        this.seriesMap = seriesMap;
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

    public void addSeries(Integer key, XYChart.Series series) {
//        seriesMap.clear();
        seriesMap.putIfAbsent(key, series);
        lineChart.getLineChart().getData().add(series);

    }

    public void removeSeries(Integer key) {
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

    public void colourSeries(LineChart<Integer, Double> chart, Series series, String name) {
        name = name.toLowerCase();
        Color color = Color.RED;
        if (name.equals("pine")) {
            color = Color.CYAN;
        } else if (name.equals("spruce")) {
            color = Color.BLUE;
        } else if (name.equals("birch")) {
            color = Color.GREEN;
        }

        String rgb = String.format("%d, %d, %d",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));

        for (int index = 0; index < series.getData().size(); index++) {
            // we're looping for each data point, changing the color of line symbol
            XYChart.Data dataPoint = (XYChart.Data) series.getData().get(index);
            Node lineSymbol = dataPoint.getNode().lookup(".chart-line-symbol");
            lineSymbol.setStyle("-fx-background-color: rgba(" + rgb + ", 1.0), white;");
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