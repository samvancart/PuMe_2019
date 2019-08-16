package pume2019.domain;

import javafx.geometry.Side;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Background;

public class PumeLineChart {

    private LineChart<Integer, Double> lineChart;

    public PumeLineChart() {

    }

    public LineChart<Integer, Double> getLineChart() {
        return lineChart;
    }

    public void setCombinedLineChart() {
        lineChart.setLegendVisible(false);
        lineChart.getXAxis().setVisible(false);
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(true);
        lineChart.setOpacity(0.5);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setHorizontalGridLinesVisible(false);
        lineChart.setVerticalGridLinesVisible(false);
        lineChart.getXAxis().setVisible(false);
        lineChart.getYAxis().setVisible(false);
        lineChart.getYAxis().setSide(Side.RIGHT);
    }

    public LineChart<Integer, Double> createLineChart(String var, String name, String unit) {
        Axis xAxis = new NumberAxis();
        Axis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        yAxis.setLabel(unit);
        yAxis.setStyle("-fx-font-size: 20");
        xAxis.setStyle("-fx-font-size: 20");
        lineChart = new LineChart(xAxis, yAxis);
        lineChart.setTitle(name);
        lineChart.setId(var);
        return lineChart;
    }

}
