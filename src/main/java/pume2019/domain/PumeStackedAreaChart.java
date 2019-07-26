package pume2019.domain;

import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;

public class PumeStackedAreaChart{

    private StackedAreaChart<Integer, Double> stackedAreaChart;

    public StackedAreaChart<Integer, Double> getStackedAreaChart() {
        return stackedAreaChart;
    }


    public StackedAreaChart<Integer, Double> createAreaChart(String var, String name, String unit) {
        Axis xAxis = new NumberAxis();
        Axis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        yAxis.setLabel(unit);
        stackedAreaChart = new StackedAreaChart(xAxis, yAxis);
        stackedAreaChart.setTitle(name);
        stackedAreaChart.setId(var);
        return stackedAreaChart;
    }


}
