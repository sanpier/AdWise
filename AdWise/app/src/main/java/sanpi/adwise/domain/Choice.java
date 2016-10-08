package sanpi.adwise.domain;

/**
 * Created by Teyfik on 28.09.2016.
 */

public class Choice {

    private int id;
    private String name;

    public Choice(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
