

package pume2019.domain;

public class Info {
    private Tree tree;
    private Heath heath;
    private String weatherPath;
    private String managPath;
    private String years;

    public Info() {
    }

    public Heath getHeath() {
        return heath;
    }

    public String getManagPath() {
        return managPath;
    }

    public Tree getTree() {
        return tree;
    }

    public String getWeatherPath() {
        return weatherPath;
    }

    public String getYears() {
        return years;
    }

    public void setHeath(Heath heath) {
        this.heath = heath;
    }

    public void setManagPath(String managPath) {
        this.managPath = managPath;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public void setWeatherPath(String weatherPath) {
        this.weatherPath = weatherPath;
    }

    public void setYears(String years) {
        this.years = years;
    }
     
    
    
}
