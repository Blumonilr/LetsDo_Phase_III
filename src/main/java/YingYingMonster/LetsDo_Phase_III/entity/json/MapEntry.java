package YingYingMonster.LetsDo_Phase_III.entity.json;

import com.google.gson.annotations.Expose;

import java.util.Map;

public class MapEntry {
    @Expose
    private String name;
    @Expose
    private Integer value;

    public MapEntry(Map.Entry<String,Integer> e) {
        this.name = e.getKey();
        this.value = e.getValue();
    }

    public MapEntry(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
