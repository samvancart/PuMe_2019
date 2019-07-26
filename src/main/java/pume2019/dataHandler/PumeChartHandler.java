package pume2019.dataHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import pume2019.domain.ChartDrawer;
import pume2019.domain.PumeButton;
import pume2019.domain.PumeLineChart;
import pume2019.domain.PumeSeries;
import pume2019.domain.PumeSeriesHandler;
import pume2019.domain.PumeStackedAreaChart;

public class PumeChartHandler {

    private ResultHandler rh;
    private PumeButton pumeBtn;
    private PumeSeriesHandler pumeSeriesHandler;
    private LineChart<Integer, Double> lineGraph;
    private StackedAreaChart<Integer, Double> stackedAreaGraph;
    private ChartDrawer drawer;

    public PumeChartHandler(PumeButton pumeBtn, ResultHandler rh) {
        drawer = new ChartDrawer();
        this.pumeBtn = pumeBtn;
        this.rh = rh;
    }

    public void createStackedAreaChartOfBiomass(BorderPane nestedBp, List<Button> buttons) {
        rh.getTotalBiomass(pumeBtn, buttons);
        PumeStackedAreaChart stackedAreaChart = new PumeStackedAreaChart();
        String unit = pumeBtn.getUnit();
        stackedAreaChart.createAreaChart(pumeBtn.getButton().getId(), "Biomass", unit);
        PumeSeries series = new PumeSeries();
        series.createSeries(pumeBtn.getDataList(0));
        Map<Integer, XYChart.Series> seriesMap = new HashMap<>();
        seriesMap.put(0, series.getSeries());
        pumeSeriesHandler = new PumeSeriesHandler(stackedAreaChart, seriesMap);
        series.getSeries().setName(pumeBtn.getButton().getText());
        pumeSeriesHandler.addSeriesToStackedAreaChart(0, series.getSeries());
        stackedAreaGraph = drawer.drawStackedAreaChart(pumeSeriesHandler);
        stackedAreaGraph.setAnimated(true);
        nestedBp.setCenter(stackedAreaGraph);
    }

    public void createLineChart(BorderPane nestedBp) {
        rh.calculate(pumeBtn);
        PumeLineChart pumeLineChart = new PumeLineChart();
        String unit = pumeBtn.getUnit();
        pumeLineChart.createLineChart(pumeBtn.getButton().getId(), pumeBtn.getButton().getText(), unit);
        PumeSeries series = new PumeSeries();
        series.createSeries(pumeBtn.getDataList(0));
        Map<Integer, XYChart.Series> seriesMap = new HashMap<>();
        seriesMap.put(0, series.getSeries());
        pumeSeriesHandler = new PumeSeriesHandler(pumeLineChart, seriesMap);
        series.getSeries().setName(pumeBtn.getButton().getText());
        pumeSeriesHandler.addSeriesToLineChart(0, series.getSeries());
//        nestedBp.getChildren().remove(graph);
        lineGraph = drawer.drawLineChart(pumeSeriesHandler);
        nestedBp.setCenter(lineGraph);
    }

    public void addTreeToChart(Map<Integer, List<String>> treeMap, int id, String name, int treeId) {
        List<String> data = new ArrayList<>();
        data = treeMap.get(id);
        PumeSeries series = new PumeSeries();
        series.createSeries(data);
        series.getSeries().setName(name);
        pumeSeriesHandler.addSeriesToLineChart(treeId, series.getSeries());
        lineGraph = drawer.drawLineChart(pumeSeriesHandler);
    }

    public void removeTreeFromChart(int treeId) {
        pumeSeriesHandler.removeSeriesFromLineChart(treeId);
        lineGraph = drawer.drawLineChart(pumeSeriesHandler);
    }

    public void removeGraph(BorderPane nestedBp) {
        nestedBp.getChildren().remove(lineGraph);
    }

    public void addMassToBioChart(List<Map<Integer, List<String>>> maps, int id, String name, int checkBoxId) {
        Map<Integer, List<String>> pine = maps.get(0);
        Map<Integer, List<String>> spruce = maps.get(1);
        Map<Integer, List<String>> birch = maps.get(2);
        List<String> data = rh.getSum(pine.get(id), spruce.get(id), birch.get(id));
        PumeSeries series = new PumeSeries();
        series.createSeries(data);
        series.getSeries().setName(name);
        pumeSeriesHandler.addSeriesToStackedAreaChart(checkBoxId, series.getSeries());
        stackedAreaGraph.setAnimated(true);
        stackedAreaGraph = drawer.drawStackedAreaChart(pumeSeriesHandler);
    }

    public void removeMassFromBioChart(int checkBoxId) {
        pumeSeriesHandler.removeSeriesFromStackedAreaChart(checkBoxId);
        stackedAreaGraph = drawer.drawStackedAreaChart(pumeSeriesHandler);
    }

}
