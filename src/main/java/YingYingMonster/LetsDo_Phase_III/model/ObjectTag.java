package YingYingMonster.LetsDo_Phase_III.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectTag {
    String objectName;
    Map<String,String[]> attributions;

    public ObjectTag(String objectName, Map<String, String[]> attributions) {
        this.objectName = objectName;
        this.attributions = attributions;
    }

    public ObjectTag(){
        this.objectName=null;
        this.attributions=null;
    }

    public String getObjectName() {
        return objectName;
    }

    public Map<String, String[]> getAttributions() {
        return attributions;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setAttributions(Map<String, String[]> attributions) {
        this.attributions = attributions;
    }

    public List<String> toStringList(){
        List<String> list=new ArrayList<>();
        list.add(this.objectName);
        for(Map.Entry<String,String[]> entry:this.attributions.entrySet()){
            String temp=entry.getKey();
            for(String s:entry.getValue()){
                temp+=("_"+s);
            }
            list.add(temp);
        }
        return list;
    }
}
