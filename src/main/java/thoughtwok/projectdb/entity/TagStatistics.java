package thoughtwok.projectdb.entity;

import java.util.HashMap;
import java.util.Map;

public class TagStatistics {
    
    private Map<Tag, Integer> tagFrequency = new HashMap<>();
    
    public void add(Tag tag, Integer count) {
        this.tagFrequency.put(tag, count);
    }

    public Map<Tag, Integer> getTagFrequency() {
        return tagFrequency;
    }

}
