package test;


import java.util.LinkedList;

public class LRU implements CacheReplacementPolicy {
    private final LinkedList<String> wordsList;
    public LRU() {
        wordsList = new LinkedList<>();
    }
    @Override
    public void add(String word) {
        if (wordsList.contains(word)) {
            wordsList.remove(word);
            return;
        }
        wordsList.add(word);
    }

    @Override
    public String remove() {
        return wordsList.removeFirst();
    }
}
