
package pume2019.domain;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;

public class PumeChart {

    private LineChart<Integer, Double> lineChart;
    private StackedAreaChart<Integer,Double> areaChart;

    public PumeChart() {
        
    }

    public LineChart<Integer, Double> getLineChart() {
        return lineChart;
    }

    public StackedAreaChart<Integer, Double> getAreaChart() {
        return areaChart;
    }
    
    public LineChart<Integer, Double> createLineChart(String var,String name,String unit) {
        Axis xAxis = new NumberAxis();
        Axis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        yAxis.setLabel(unit);
        lineChart = new LineChart(xAxis, yAxis);
        lineChart.setTitle(name);
        lineChart.setId(var);
        return lineChart;
    }
    
        public StackedAreaChart<Integer, Double> createAreaChart(String var,String name,String unit) {
        Axis xAxis = new NumberAxis();
        Axis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        yAxis.setLabel(unit);
        areaChart = new StackedAreaChart(xAxis, yAxis);
        areaChart.setTitle(name);
        areaChart.setId(var);
        return areaChart;
    }
}

