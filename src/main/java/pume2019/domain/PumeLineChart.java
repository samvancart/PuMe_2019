
package pume2019.domain;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class PumeLineChart {

    private LineChart<Integer, Double> lineChart;

    public PumeLineChart() {
        
    }

    public LineChart<Integer, Double> getLineChart() {
        return lineChart;
    }

    public LineChart<Integer, Double> createChart(String var,String name) {
        Axis xAxis = new NumberAxis();
        Axis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        lineChart = new LineChart(xAxis, yAxis);
        lineChart.setTitle(name);
        lineChart.setId(var);
        return lineChart;
    }
}
