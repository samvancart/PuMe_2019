package pume2019.dataHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pume2019.domain.ChartDrawer;
import pume2019.domain.Info;
import pume2019.domain.PumeButton;
import pume2019.domain.PumeLineChart;
import pume2019.domain.PumeSeries;
import pume2019.domain.PumeSeriesHandler;
import pume2019.domain.PumeStackedAreaChart;
import pume2019.domain.PumeStackedBarChart;

public class PumeChartHandler {

    private ResultHandler rh;
    private PumeButton pumeBtn;
    private PumeSeriesHandler pumeSeriesHandler;
    private LineChart<Integer, Double> lineGraph;
    private StackedAreaChart<Integer, Double> stackedAreaGraph;
    private StackedBarChart<String, Double> stackedBarGraph;
    private ChartDrawer drawer;

    public PumeChartHandler(PumeButton pumeBtn, ResultHandler rh) {
        drawer = new ChartDrawer();
        this.pumeBtn = pumeBtn;
        this.rh = rh;
    }

    public void createRemovalsCharts(BorderPane nestedBp) {
        StackPane stackPane = new StackPane();
        this.createCombinedStackedBarChart();
        Button button = new Button("Mortality");
        button.setId("42");
        pumeBtn = new PumeButton(button);
        this.createCombinedLineChart();
        stackPane.getChildren().addAll(stackedBarGraph, lineGraph);
        nestedBp.setCenter(stackPane);
    }

    public void createCombinedLineChart() {
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
        pumeLineChart.setCombinedLineChart();
        lineGraph = drawer.drawLineChart(pumeSeriesHandler);
    }

    public void createCombinedStackedBarChart() {
        rh.calculate(pumeBtn);
        PumeStackedBarChart stackedBarChart = new PumeStackedBarChart();
        String unit = pumeBtn.getUnit();
        stackedBarChart.createStackedBarChart(pumeBtn.getButton().getId(), "Cuttings", unit);
        PumeSeries series = new PumeSeries();
        series.createSeriesForStackedBarChart(pumeBtn.getDataList(0), null);
        Map<Integer, XYChart.Series> seriesMap = new HashMap<>();
        seriesMap.put(0, series.getSeries());
        pumeSeriesHandler = new PumeSeriesHandler(stackedBarChart, seriesMap);
        series.getSeries().setName(pumeBtn.getButton().getText());
        pumeSeriesHandler.addSeriesToStackedBarChart(0, series.getSeries());
        stackedBarGraph = drawer.drawStackedBarChart(pumeSeriesHandler);
        stackedBarChart.setCombinedStackedBarChart();
    }

    public void createStackedBarChart(BorderPane nestedBp) {
        rh.calculate(pumeBtn);
        PumeStackedBarChart stackedBarChart = new PumeStackedBarChart();
        String unit = pumeBtn.getUnit();
        stackedBarChart.createStackedBarChart(pumeBtn.getButton().getId(), "Cuttings", unit);
        PumeSeries series = new PumeSeries();
        series.createSeriesForStackedBarChart(pumeBtn.getDataList(0), null);
        Map<Integer, XYChart.Series> seriesMap = new HashMap<>();
        seriesMap.put(0, series.getSeries());
        pumeSeriesHandler = new PumeSeriesHandler(stackedBarChart, seriesMap);
        series.getSeries().setName(pumeBtn.getButton().getText());
        pumeSeriesHandler.addSeriesToStackedBarChart(0, series.getSeries());
        stackedBarGraph = drawer.drawStackedBarChart(pumeSeriesHandler);
        stackedBarGraph.setAnimated(true);
        nestedBp.setCenter(stackedBarGraph);
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

    public void createTHCBLineChart(BorderPane nestedBp) {
        pumeBtn.getButton().setId("11");
        rh.calculate(pumeBtn);
        PumeLineChart pumeLineChart = new PumeLineChart();
        String unit = pumeBtn.getUnit();
        pumeLineChart.createLineChart(pumeBtn.getButton().getId(), pumeBtn.getButton().getText(), unit);
        PumeSeries series = new PumeSeries();
        series.createSeries(pumeBtn.getDataList(0));
        Map<Integer, XYChart.Series> seriesMap = new HashMap<>();
        seriesMap.put(0, series.getSeries());
        pumeSeriesHandler = new PumeSeriesHandler(pumeLineChart, seriesMap);
        series.getSeries().setName("Tree height");
        pumeSeriesHandler.addSeriesToLineChart(0, series.getSeries());
        lineGraph = drawer.drawLineChart(pumeSeriesHandler);
        nestedBp.setCenter(lineGraph);
        this.addCrownBaseToChart();
    }

    public void addCrownBaseToChart() {
        pumeBtn.getButton().setId("14");
        rh.calculate(pumeBtn);
        PumeSeries series = new PumeSeries();
        series.createSeries(pumeBtn.getDataList(1));
        series.getSeries().setName("Crown base");
        pumeSeriesHandler.addSeriesToLineChart(14, series.getSeries());
        lineGraph = drawer.drawLineChart(pumeSeriesHandler);
    }

    public void addTreeToTHCBChart(Map<Integer, List<String>> treeMap, int id, String name, int treeId) {
        List<String> data = new ArrayList<>();
        data = treeMap.get(11);
        PumeSeries series = new PumeSeries();
        series.createSeries(data);
        series.getSeries().setName(name + " (Tree height)");
        pumeSeriesHandler.addSeriesToLineChart(treeId, series.getSeries());
        data = treeMap.get(14);
        series = new PumeSeries();
        series.createSeries(data);
        series.getSeries().setName(name + " (Crown base)");
        pumeSeriesHandler.addSeriesToLineChart(treeId + 4, series.getSeries());
        lineGraph = drawer.drawLineChart(pumeSeriesHandler);
    }

    public void addTreeToChart(PumeButton pumeBtn, List<Map<Integer, List<String>>> maps, Map<Integer, List<String>> treeMap, int id, String name, int treeId) {
        if (id == 14 || id == 11) {
            this.addTreeToTHCBChart(treeMap, id, name, treeId);
        } else if (id == 37) {
            this.addTreeToStackedBarChart(treeMap, id, name, treeId);
        } else if (id == 18 || id == 9) {
//        } else if (id == 10 || id == 18 || id == 9) {
            //CREATE BOOLEAN FLAG 
//            if (info.getTree().getId() != 0) {
//                List<String> sum;
//                sum = rh.getSum(maps.get(0).get(id), maps.get(1).get(id), maps.get(2).get(id));
//                treeMap.put(id, sum);
//            }
            this.addTreeToChartAndConvertToGrams(treeMap, id, name, treeId);

        } else {
            List<String> data = new ArrayList<>();
            data = treeMap.get(id);
            PumeSeries series = new PumeSeries();
            series.createSeries(data);
            series.getSeries().setName(name);
            pumeSeriesHandler.addSeriesToLineChart(treeId, series.getSeries());
            lineGraph = drawer.drawLineChart(pumeSeriesHandler);
        }
    }

    public void addTreeToChartAndConvertToGrams(Map<Integer, List<String>> treeMap, int id, String name, int treeId) {
        List<String> data = new ArrayList<>();
        data = this.convertListValuesToGrams(treeMap, id);
        PumeSeries series = new PumeSeries();
        series.createSeries(data);
        series.getSeries().setName(name);
        pumeSeriesHandler.addSeriesToLineChart(treeId, series.getSeries());
        lineGraph = drawer.drawLineChart(pumeSeriesHandler);
    }

    public List<String> convertListValuesToGrams(Map<Integer, List<String>> treeMap, int id) {
        List<String> data = new ArrayList<>();
        List<String> newData = new ArrayList<>();
        data = treeMap.get(id);
        for (String val : data) {
            Double valD = Double.parseDouble(val);
            valD *= 1000;
            val = String.valueOf(valD);
            newData.add(val);
        }
        return newData;
    }

    public void addTreeToStackedBarChart(Map<Integer, List<String>> treeMap, int id, String name, int treeId) {
        List<String> data = new ArrayList<>();
        data = treeMap.get(id);
        PumeSeries series = new PumeSeries();
        series.createSeriesForStackedBarChart(data, pumeBtn.getDataList(0));
        series.getSeries().setName(name);
        pumeSeriesHandler.addSeriesToStackedBarChart(treeId, series.getSeries());
        stackedBarGraph = drawer.drawStackedBarChart(pumeSeriesHandler);
    }

    public void removeTreeFromChart(int treeId) {
        if (pumeBtn.getButton().getId().equals("14")) {
            pumeSeriesHandler.removeSeriesFromLineChart(treeId);
            pumeSeriesHandler.removeSeriesFromLineChart(treeId + 4);
            lineGraph = drawer.drawLineChart(pumeSeriesHandler);
        } else if (pumeBtn.getButton().getId().equals("37")) {
            pumeSeriesHandler.removeSeriesFromStackedBarChart(treeId);
        } else {
            pumeSeriesHandler.removeSeriesFromLineChart(treeId);
            lineGraph = drawer.drawLineChart(pumeSeriesHandler);
        }

    }

    public void removeGraph(BorderPane nestedBp) {
        if (lineGraph != null) {
            nestedBp.getChildren().remove(lineGraph);
        }
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

    public void removeFromLineChart(int id) {
        pumeSeriesHandler.removeSeriesFromLineChart(id);
        lineGraph = drawer.drawLineChart(pumeSeriesHandler);
    }

    public void removeFromStackedBarChart(int id) {
        pumeSeriesHandler.removeSeriesFromStackedBarChart(id);
        stackedBarGraph = drawer.drawStackedBarChart(pumeSeriesHandler);
    }
}
