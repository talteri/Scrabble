package test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Dictionary {
    private final CacheManager existingWordsCache;
    private final CacheManager nonExistingWordsCache;
    private final BloomFilter bloomFilter;
    CacheReplacementPolicy lru;
    CacheReplacementPolicy lfu;
    private final String[] fileNames;

    public Dictionary(String... fileNames) {
        // Initialize the CacheManagers and BloomFilter
        lru = new LRU();
        existingWordsCache = new CacheManager(400, lru);
        lfu = new LFU();
        nonExistingWordsCache = new CacheManager(100, lfu);
        bloomFilter = new BloomFilter(256, "MD5", "SHA1");
        this.fileNames = fileNames.clone();
        // Insert words from the files into the BloomFilter
        for (String fileName : fileNames) {
            addWordsToBloom(fileName);
        }
    }

    public boolean query(String word) {
        // Check the existing words cache
        boolean result = existingWordsCache.query(word);
        if (result) {
            return true;
        }

        // Check the non-existing words cache
        result = nonExistingWordsCache.query(word);
        if (result) {
            return false;
        }

        // Check the BloomFilter and update the cache
        if (bloomFilter.contains(word)) {
            existingWordsCache.add(word);
            return true;
        } else {
            nonExistingWordsCache.add(word);
            return false;
        }
    }




    void addWordsToBloom(String FileName)
    {
        try{
            Scanner s = new Scanner(new File(Paths.get(FileName).toUri()));
            while(s.hasNext())
                bloomFilter.add(s.next());
        }catch (FileNotFoundException E){ System.err.println("File wasn't found");
        }
    }


    public boolean challenge(String word) {
        try {
            boolean found = IOSearcher.search(word, fileNames);
            if (found) {
                existingWordsCache.add(word);
            } else {
                nonExistingWordsCache.add(word);
            }
            return found;
        } catch (IOException e) {
            System.err.println("IO Error");
            return false;
        }
    }
}
