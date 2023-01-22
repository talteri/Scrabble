package test;

import java.util.HashSet;
import java.util.Set;

public class CacheManager {
    private final Set<String> cache;
    private final CacheReplacementPolicy crp;
    private final int maxSize;
    /*
    - `cache` is the data structure that stores the cache
    - `crp` is an object that implements the CacheReplacementPolicy interface, which is used to determine which elements should be removed from the cache when it becomes full
    - `maxSize` is the maximum number of elements that the cache can hold
     */
    public CacheManager(int maxSize, CacheReplacementPolicy crp) {
        this.maxSize = maxSize;
        this.crp = crp;
        this.cache = new HashSet<>();
    }
    /*
    - This is the constructor for the class, it takes in 2 parameters `maxSize` and `crp`
    - It sets the `maxSize` variable to the value passed
    - It sets the `crp` variable to the value passed
    - It creates a new `HashSet` object and assigns it to the `cache` variable
     */

    public boolean query(String word) {
        return cache.contains(word);
    }
    /*
    - This method takes in a string `word` and checks if the word is present in the cache
    - It does this by calling the `contains` method of the `Set` interface on the `cache` variable
    - It returns `true` if the word is present in the cache and `false` otherwise
     */

    public void add(String word) {
        crp.add(word);
        if (cache.size() >= maxSize) {
            String victim = crp.remove();
            cache.remove(victim);
        }
        cache.add(word);
    }
    /*
    - This method takes in a string `word` and adds it to the cache
    - It first calls the `add` method on the `crp` object to indicate that the word is being added to the cache
    - It then checks if the size of the cache is greater than or equal to the `maxSize` variable
    - If the size of the cache is greater than or equal to the `maxSize` variable, it removes the least-recently-used element from the cache by calling the `remove` method on the `crp` object
    - Finally, it adds the word to the cache using the `add` method of the `Set` interface
     */
}
