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
//        pineMap=maps.get(0);
//        spruceMap=maps.get(1);
//        birchMap=maps.get(2);
        if (idMap.get(id) != null) {
            if (null != idMap.get(id)) {
                switch (idMap.get(id)) {
                    case 1:
                        List<String> avg;
                        avg = getAvg(pineMap.get(Integer.parseInt(id)), spruceMap.get(Integer.parseInt(id)), birchMap.get(Integer.parseInt(id)));
                        button.addToList(avg);
                        break;
                    case 2:
                        if (id.equals("44") && !conversionDone) {
                            convertToGrams(pineMap.get(Integer.parseInt(id)), spruceMap.get(Integer.parseInt(id)), birchMap.get(Integer.parseInt(id)), Integer.parseInt(id));
                        }
                        List<String> sum;
                        sum = getSum(pineMap.get(Integer.parseInt(id)), spruceMap.get(Integer.parseInt(id)), birchMap.get(Integer.parseInt(id)));
                        button.addToList(sum);
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            }
        }
    }

    //Convert to grams
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
        System.out.println("Results: "+ results);
        return results;
    }
        public List<String> getMinusSum(List<String> pine, List<String> spruce, List<String> birch) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < pine.size(); i++) {
            Double pineD = Double.parseDouble(pine.get(i));
            Double spruceD = Double.parseDouble(spruce.get(i));
            Double birchD = Double.parseDouble(birch.get(i));
            Double sumD = pineD+spruceD+birchD; 
            String sum = String.valueOf(sumD*(-1));
            results.add(sum);
        }
        System.out.println("Results: "+ results);
        return results;
    }

    // TOTAL BIOMASS
    public List<String> getTotalBiomass(PumeButton pumeButton,List<Button> buttons) {
        List<List<String>> dataLists = new ArrayList<>();
        List<String> results = new ArrayList<>();
        for (Button button : buttons) {
            PumeButton pumeBtn = new PumeButton(button);
            this.calculate(pumeBtn);
            dataLists.add(pumeBtn.getDataList(0));
        }
        for (int i = 0; i < dataLists.get(0).size(); i++) {
           int dataListsFirstSize=dataLists.get(0).size();
            Double sum = 0.0;
            for (int j = 0; j < dataLists.size(); j++) {
                int dataListsSize = dataLists.size();
                sum += Double.parseDouble(dataLists.get(j).get(i));
            }
            results.add(String.valueOf(sum));
        }
        pumeButton.addToList(results);
        System.out.println("Summed up: "+results);
        return results;
    }

//        public List<String> convertToGrams(List<String> pine, List<String> spruce, List<String> birch) {
//        List<String> results = new ArrayList<>();
//        for (int i = 0; i < pine.size(); i++) {
//            Double pineD = Double.parseDouble(pine.get(i));
//            Double spruceD = Double.parseDouble(spruce.get(i));
//            Double birchD = Double.parseDouble(birch.get(i));
//            String sum = String.valueOf((pineD + spruceD + birchD));
//            results.add(sum);
//        }
//        return results;
//    }
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
