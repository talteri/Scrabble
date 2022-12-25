package test;

import java.util.HashSet;
import java.util.Set;

public class CacheManager {
    private final Set<String> cache;
    private final CacheReplacementPolicy crp;
    private final int maxSize;
    public CacheManager(int maxSize, CacheReplacementPolicy crp) {
        this.maxSize = maxSize;
        this.crp = crp;
        this.cache = new HashSet<>();
    }

    public boolean query(String word) {
        return cache.contains(word);
    }

    public void add(String word) {
        crp.add(word);
        if (cache.size() >= maxSize) {
            String victim = crp.remove();
            cache.remove(victim);
        }
        cache.add(word);
    }
}
