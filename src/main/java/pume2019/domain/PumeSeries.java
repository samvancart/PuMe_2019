package pume2019.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class PumeSeries {

    private XYChart.Series series;

    public PumeSeries() {
        series = new XYChart.Series();
    }

    public XYChart.Series getSeries() {
        return series;
    }

    public XYChart.Series createSeries(List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            Double dataD = Double.parseDouble(data.get(i));
            series.getData().add(new XYChart.Data(i + 1, dataD));
        }
        return series;
    }

    public XYChart.Series createSeriesForStackedBarChart(List<String> data, List<String> removalsList) {
        for (int i = 0; i < data.size(); i++) {
            Double dataD = Double.parseDouble(data.get(i));
            String iStr = String.valueOf(i + 1);
            if (removalsList != null) {
                Double removalsListD = Double.parseDouble(removalsList.get(i));
                if (removalsListD != 0 || i == 0 || i == data.size() - 1) {
                    series.getData().add(new XYChart.Data(iStr, dataD));
                }
            } else {
                if (dataD != 0 || i == 0 || i == data.size() - 1) {
                    series.getData().add(new XYChart.Data(iStr, dataD));
                }
            }

        }

        return series;
    }

}
