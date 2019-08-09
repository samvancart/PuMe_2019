package pume2019.domain;

public class Thinning {

    private String name;
    private int id;
    private String Rparam;
    private int defaultThin;
    private int ClCut;

    public Thinning(String name, int id, String Rparam, int defaultThin, int ClCut) {
        this.name = name;
        this.id = id;
        this.Rparam = Rparam;
        this.defaultThin = defaultThin;
        this.ClCut = ClCut;
    }

    public void setClCut(int ClCut) {
        this.ClCut = ClCut;
    }

    public void setDefaultThin(int defaultThin) {
        this.defaultThin = defaultThin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRparam(String Rparam) {
        this.Rparam = Rparam;
    }

    public int getDefaultThin() {
        return defaultThin;
    }

    public int getClCut() {
        return ClCut;
    }

    public String getRparam() {
        return Rparam;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

}
