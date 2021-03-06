package pume2019.dataHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Button;
import pume2019.domain.PumeButton;

public class ResultHandler {

    private Map<String, Integer> idMap;
    private Map<Integer, List<String>> pineMap;
    private Map<Integer, List<String>> spruceMap;
    private Map<Integer, List<String>> birchMap;
    private boolean conversionDone = false;

    public boolean isConversionDone() {
        return conversionDone;
    }

    public void setConversionDone(boolean conversionDone) {
        this.conversionDone = conversionDone;
    }

    public void resetConversionDone() {
        this.conversionDone = false;
    }

    public ResultHandler() {
        idMap = new HashMap<>();

    }

    public void addIdToMap(String id, int calc) {
        String[] parts = id.split(" ");
        for (String part : parts) {
            idMap.put(part, calc);
        }
    }

    // Find from map
    public void calculate(PumeButton button) {
        String id = button.getButton().getId();
        if (idMap.get(id) != null) {
            if (null != idMap.get(id)) {
                switch (idMap.get(id)) {
                    case 1:
                        List<String> avg;
                        avg = getAvg(pineMap.get(Integer.parseInt(id)), spruceMap.get(Integer.parseInt(id)), birchMap.get(Integer.parseInt(id)));
                        button.addToList(avg);
                        break;
                    case 2:
                        List<String> sum;
                        sum = getSum(pineMap.get(Integer.parseInt(id)), spruceMap.get(Integer.parseInt(id)), birchMap.get(Integer.parseInt(id)));
                        button.addToList(sum);
                        break;
                    case 3:
                        break;
                    case 4:
                        List<String> waterUse;
                        waterUse = getWaterUse(pineMap.get(Integer.parseInt("22")));
                        button.addToList(waterUse);
                        break;
                    case 5:
                        List<String> weightedAvg;
                        weightedAvg = getWeightedAvg(pineMap.get(Integer.parseInt(id)), spruceMap.get(Integer.parseInt(id)), birchMap.get(Integer.parseInt(id)),
                                pineMap.get(Integer.parseInt("17")), spruceMap.get(Integer.parseInt("17")), birchMap.get(Integer.parseInt("17")));
                        button.addToList(weightedAvg);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public List<String> getWaterUse(List<String> pine) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < pine.size(); i++) {
            results.add("0");
        }
        return results;
    }

    public void setConvertedVals(List<Map<Integer, List<String>>> maps, int oldId, int newId) {
        List<String> newPine = new ArrayList<>();
        List<String> newSpruce = new ArrayList<>();
        List<String> newBirch = new ArrayList<>();
        List<String> pine = maps.get(0).get(oldId);
        List<String> spruce = maps.get(1).get(oldId);
        List<String> birch = maps.get(2).get(oldId);
        for (int i = 0; i < pine.size(); i++) {
            Double pineD = Double.parseDouble(pine.get(i));
            newPine.add(String.valueOf(pineD));
            Double spruceD = Double.parseDouble(spruce.get(i));
            newSpruce.add(String.valueOf(spruceD));
            Double birchD = Double.parseDouble(birch.get(i));
            newBirch.add(String.valueOf(birchD));
        }
        pineMap.replace(newId, this.getSum(newPine, newSpruce, newBirch));
        spruceMap.replace(newId, this.getSum(newPine, newSpruce, newBirch));
        birchMap.replace(newId, this.getSum(newPine, newSpruce, newBirch));
    }

    public void convertToGrams(List<String> pine, List<String> spruce, List<String> birch, int id) {
        List<String> newPine = new ArrayList<>();
        List<String> newSpruce = new ArrayList<>();
        List<String> newBirch = new ArrayList<>();
        for (int i = 0; i < pine.size(); i++) {
            Double pineD = Double.parseDouble(pine.get(i));
            pineD *= 1000;
            newPine.add(String.valueOf(pineD));
            Double spruceD = Double.parseDouble(spruce.get(i));
            spruceD *= 1000;
            newSpruce.add(String.valueOf(spruceD));
            Double birchD = Double.parseDouble(birch.get(i));
            birchD *= 1000;
            newBirch.add(String.valueOf(birchD));
        }

        pineMap.replace(id, newPine);
        spruceMap.replace(id, newSpruce);
        birchMap.replace(id, newBirch);
        conversionDone = true;
    }

    public List<String> getAvg(List<String> pine, List<String> spruce, List<String> birch) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < pine.size(); i++) {
            Double pineD = Double.parseDouble(pine.get(i));
            Double spruceD = Double.parseDouble(spruce.get(i));
            Double birchD = Double.parseDouble(birch.get(i));
            String avg = String.valueOf((pineD + spruceD + birchD) / 3);
            results.add(avg);
        }
        return results;
    }

    public List<String> getSum(List<String> pine, List<String> spruce, List<String> birch) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < pine.size(); i++) {
            Double pineD = Double.parseDouble(pine.get(i));
            Double spruceD = Double.parseDouble(spruce.get(i));
            Double birchD = Double.parseDouble(birch.get(i));
            String sum = String.valueOf((pineD + spruceD + birchD));
            results.add(sum);
        }
        return results;
    }

    public List<String> getWeightedAvg(List<String> pine, List<String> spruce, List<String> birch,
            List<String> sdPine, List<String> sdSpruce, List<String> sdBirch) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < pine.size(); i++) {
            Double pineD = Double.parseDouble(pine.get(i));
            Double spruceD = Double.parseDouble(spruce.get(i));
            Double birchD = Double.parseDouble(birch.get(i));
            Double sdPineD = Double.parseDouble(sdPine.get(i));
            Double sdSpruceD = Double.parseDouble(sdSpruce.get(i));
            Double sdBirchD = Double.parseDouble(sdBirch.get(i));
            String avg = String.valueOf(((pineD * sdPineD) + (spruceD * sdSpruceD) + (birchD * sdBirchD)) / (sdPineD + sdSpruceD + sdBirchD));
            if (avg.equals("NaN")) {
                avg = "0";
            }
            results.add(avg);
        }
        return results;
    }

    public List<String> getMinusSum(List<String> pine, List<String> spruce, List<String> birch) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < pine.size(); i++) {
            Double pineD = Double.parseDouble(pine.get(i));
            Double spruceD = Double.parseDouble(spruce.get(i));
            Double birchD = Double.parseDouble(birch.get(i));
            Double sumD = pineD + spruceD + birchD;
            String sum = String.valueOf(sumD * (-1));
            results.add(sum);
        }
        return results;
    }

    // TOTAL BIOMASS
    public List<String> getTotalBiomass(PumeButton pumeButton, List<Button> buttons) {
        List<List<String>> dataLists = new ArrayList<>();
        List<String> results = new ArrayList<>();
        for (Button button : buttons) {
            PumeButton pumeBtn = new PumeButton(button);
            this.calculate(pumeBtn);
            dataLists.add(pumeBtn.getDataList(0));
        }
        for (int i = 0; i < dataLists.get(0).size(); i++) {
            Double sum = 0.0;
            for (int j = 0; j < dataLists.size(); j++) {
                sum += Double.parseDouble(dataLists.get(j).get(i));
            }
            results.add(String.valueOf(sum));
        }
        pumeButton.addToList(results);
        return results;
    }

    public Map<String, Integer> getIdMap() {
        return idMap;
    }

    public Map<Integer, List<String>> getBirchMap() {
        return birchMap;
    }

    public Map<Integer, List<String>> getSpruceMap() {
        return spruceMap;
    }

    public Map<Integer, List<String>> getPineMap() {
        return pineMap;
    }

    public void setBirchMap(Map<Integer, List<String>> birchMap) {
        this.birchMap = birchMap;
    }

    public void setSpruceMap(Map<Integer, List<String>> spruceMap) {
        this.spruceMap = spruceMap;
    }

    public void setPineMap(Map<Integer, List<String>> pineMap) {
        this.pineMap = pineMap;
    }

    public void setMaps(List<Map<Integer, List<String>>> maps) {
        setPineMap(maps.get(0));
        setSpruceMap(maps.get(1));
        setBirchMap(maps.get(2));
    }

}
