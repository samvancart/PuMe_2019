package pume2019.dataHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import pume2019.domain.LineChartDrawer;
import pume2019.domain.PumeButton;
import pume2019.domain.PumeLineChart;
import pume2019.domain.PumeSeries;
import pume2019.domain.PumeSeriesHandler;

public class PumeChartHandler {

    private ResultHandler rh;
    private PumeButton pumeBtn;
    private PumeSeriesHandler pumeSeriesHandler;
    private LineChart<Integer, Double> graph;
    private LineChartDrawer drawer;

    public PumeChartHandler(PumeButton pumeBtn, ResultHandler rh) {
        drawer = new LineChartDrawer();
        this.pumeBtn = pumeBtn;
        this.rh = rh;
    }

    
    public void createLineChart(BorderPane nestedBp) {
        rh.calculate(pumeBtn);
        PumeLineChart pumeLineChart = new PumeLineChart();
        pumeLineChart.createChart(pumeBtn.getButton().getId(), pumeBtn.getButton().getText());
        PumeSeries series = new PumeSeries();
        series.createSeries(pumeBtn.getDataList(0));
        Map<Integer, XYChart.Series> seriesMap = new HashMap<>();
        seriesMap.put(0, series.getSeries());
        pumeSeriesHandler = new PumeSeriesHandler(pumeLineChart, seriesMap);
        pumeSeriesHandler.addSeries(0, series.getSeries());
//        nestedBp.getChildren().remove(graph);
        graph = drawer.drawChart(pumeSeriesHandler);
        nestedBp.setCenter(graph);
    }

    public void addToChart(Map<Integer, List<String>> treeMap, int id, String name, int treeId) {
        List<String> data = new ArrayList<>();
        data = treeMap.get(id);
        PumeSeries series = new PumeSeries();
        series.createSeries(data);
        series.getSeries().setName(name);
        pumeSeriesHandler.addSeries(treeId, series.getSeries());
        graph = drawer.drawChart(pumeSeriesHandler);
    }

    public void removeFromChart(int treeId) {
        pumeSeriesHandler.removeSeries(treeId);
        graph = drawer.drawChart(pumeSeriesHandler);
    }

    public void removeGraph(BorderPane nestedBp) {
            nestedBp.getChildren().remove(graph);
    }

}
