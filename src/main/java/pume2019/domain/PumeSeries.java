
package pume2019.domain;

import java.util.List;
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

    
}

