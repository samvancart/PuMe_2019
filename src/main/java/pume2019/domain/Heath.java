
package pume2019.domain;

public class Heath {
    private String name;
    private int id;

    public Heath(String name, int id) {
        this.name = name;
        this.id = id;
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
