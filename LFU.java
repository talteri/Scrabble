package test;

import java.util.LinkedHashMap;
import java.util.Map;

public class LFU implements CacheReplacementPolicy {
    private final LinkedHashMap<String, Integer> wordsMap;
    public LFU() { wordsMap = new LinkedHashMap<>();}
    @Override
    public void add(String word) {
        int counter = 0;
        if(wordsMap.containsKey(word))
        {
            counter = wordsMap.get(word)+1;
            wordsMap.replace(word,counter);
        }
        else
            wordsMap.put(word,1);
    }
    @Override
    public String remove() {return findVictim();}
    private String findVictim() {
        int minCount = Integer.MAX_VALUE;
        String victim = null;
        for (Map.Entry<String, Integer> entry : wordsMap.entrySet()) {
            if (entry.getValue() < minCount) {
                victim = entry.getKey();
                minCount = entry.getValue();
            }
        }
        return victim;
    }
}
