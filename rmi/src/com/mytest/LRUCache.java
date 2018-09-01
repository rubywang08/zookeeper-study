package src.com.mytest;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<k, v> {
    private final int maxCacheSize;
    private final float loadfactor = 0.75f;
    private LinkedHashMap<k, v> cache;

    public LRUCache(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
        int capacity = (int) Math.ceil(maxCacheSize / loadfactor) + 1;
        cache = new LinkedHashMap(capacity, loadfactor, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > maxCacheSize;
            }
        };
    }

    public static void main(String[] args) {
        LRUCache<Integer, Object> cache = new LRUCache<>(5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        System.out.println(cache);
        cache.get(2);
        System.out.println(cache);
        cache.put(4, 4);
        cache.put(5, 5);
        System.out.println(cache);
        cache.put(6, 6);
        System.out.println(cache);
    }

    public synchronized void put(k key, v value) {
        cache.put(key, value);
    }

    public synchronized v get(k key) {
        return cache.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<k, v> entry : cache.entrySet()) {
            sb.append(entry.getKey() + "->" + entry.getValue()).append(" ");
        }
        return sb.toString();
    }
}
