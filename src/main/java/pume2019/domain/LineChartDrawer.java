
package pume2019.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class LineChartDrawer {

    private LineChart<Integer, Double> handlerChart;

    public LineChartDrawer() {

    }



    public LineChart<Integer, Double> drawChart(PumeSeriesHandler handler) {
        handlerChart = handler.getLineChart().getLineChart();
        this.handleColourSeries(handler);
        return handlerChart;
    }

    public void handleColourSeries(PumeSeriesHandler handler) {
        Map<Integer, XYChart.Series> seriesMap = handler.getSeriesMap();

        for (Map.Entry<Integer, Series> entry : seriesMap.entrySet()) {
            XYChart.Series series = seriesMap.get(entry.getKey());
            System.out.println(seriesMap.get(entry.getKey()));
            if (seriesMap.get(entry.getKey()) != null) {
                if (null == entry.getKey()) {
                    handler.colourSeries(handlerChart, series, "default");
                } else {
                    switch (entry.getKey()) {
                        case 1:
                            handler.colourSeries(handlerChart, series, "pine");
                            break;
                        case 2:
                            handler.colourSeries(handlerChart, series, "spruce");
                            break;
                        case 3:
                            handler.colourSeries(handlerChart, series, "birch");
                            break;
                        default:
                            handler.colourSeries(handlerChart, series, "default");
                            break;
                    }
                }
            }
        }
    }

    public LineChart<Integer, Double> create(List<String> data, String var) {
        Axis xAxis = new NumberAxis();
        Axis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        handlerChart = new LineChart(xAxis, yAxis);
        handlerChart.setTitle("Variable " + var);
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < data.size(); i++) {
            Double dataD = Double.parseDouble(data.get(i));
            series.getData().add(new XYChart.Data(i + 1, dataD));
        }
        handlerChart.getData().addAll(series);
        return handlerChart;
    }

    public LineChart<Integer, Double> sumChart(HashMap<Integer, List<String>> pine, HashMap<Integer, List<String>> spruce, HashMap<Integer, List<String>> birch, int var) {
        Axis xAxis = new NumberAxis();
        Axis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        handlerChart = new LineChart(xAxis, yAxis);
        handlerChart.setTitle("Variable " + var);
        XYChart.Series series = new XYChart.Series();
        ArrayList<String> pineList = (ArrayList<String>) pine.get(var);
        ArrayList<String> spruceList = new ArrayList<>();
        ArrayList<String> birchList = new ArrayList<>();
        pineList = (ArrayList<String>) pine.get(var);
        spruceList = (ArrayList<String>) spruce.get(var);
        birchList = (ArrayList<String>) birch.get(var);
        for (int i = 0; i < pineList.size(); i++) {
            Double pineD = Double.parseDouble(pineList.get(i));
            Double spruceD = Double.parseDouble(spruceList.get(i));
            Double birchD = Double.parseDouble(birchList.get(i));
            Double dataD = pineD + spruceD + birchD;
            series.getData().add(new XYChart.Data(i + 1, dataD));
        }
        handlerChart.getData().addAll(series);
        return handlerChart;
    }

}

