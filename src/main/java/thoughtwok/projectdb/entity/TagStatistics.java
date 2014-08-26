package thoughtwok.projectdb.entity;

import java.util.HashMap;
import java.util.Map;

public class TagStatistics {
    
    private Map<String, Integer> tagFrequency = new HashMap<>();
    
    public void add(String tag, Integer count) {
        this.tagFrequency.put(tag, count);
    }

    public Map<String, Integer> getTagFrequency() {
        return tagFrequency;
    }

    @Override
    public String toString() {
        return "TagStatistics [tagFrequency=" + tagFrequency + "]";
    }
    
    

}
