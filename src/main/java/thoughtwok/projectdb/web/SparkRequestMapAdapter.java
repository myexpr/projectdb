package thoughtwok.projectdb.web;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import spark.Request;

public class SparkRequestMapAdapter implements Map<String, String> {

    private Request request = null;
    
    public SparkRequestMapAdapter(Request request) {
        super();
        this.request = request;
    }
    
    @Override
    public void clear() {
        // do nothing
        throw new RuntimeException("Not supported");
    }

    @Override
    public boolean containsKey(Object key) {
        String result = request.queryParams((String) key);
        if ( result != null || result.length() > 0 ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsValue(Object value) {
        // do nothing. hopefully we never need to use this
        throw new RuntimeException("Not supported");
    }

    @Override
    public Set<java.util.Map.Entry<String, String>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String get(Object key) {
        String result = request.queryParams((String) key);
        return result;
    }

    @Override
    public boolean isEmpty() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public Set<String> keySet() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public String put(String key, String value) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public String remove(Object key) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public int size() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public Collection<String> values() {
        throw new RuntimeException("Not supported");
    }
}
