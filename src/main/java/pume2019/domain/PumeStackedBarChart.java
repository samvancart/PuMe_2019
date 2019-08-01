
package pume2019.domain;

import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;

public class PumeStackedBarChart {
        private StackedBarChart<String, Double> stackedBarChart;

    public PumeStackedBarChart() {

    }

    public StackedBarChart<String, Double> getStackedBarChart() {
        return stackedBarChart;
    }

        public void setCombinedStackedBarChart() {
        stackedBarChart.setLegendVisible(false);
        stackedBarChart.setAnimated(false);
        stackedBarChart.setAlternativeRowFillVisible(false);
        stackedBarChart.setAlternativeColumnFillVisible(false);
        stackedBarChart.setHorizontalGridLinesVisible(false);
        stackedBarChart.setVerticalGridLinesVisible(false);
        stackedBarChart.getXAxis().setVisible(false);
        stackedBarChart.getYAxis().setVisible(false);
    }


    public StackedBarChart<String, Double> createStackedBarChart(String var, String name, String unit) {
        Axis xAxis = new CategoryAxis();
        Axis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        yAxis.setLabel(unit);
        stackedBarChart = new StackedBarChart(xAxis, yAxis);
        stackedBarChart.setTitle(name);
        stackedBarChart.setId(var);
        return stackedBarChart;
    }
}
