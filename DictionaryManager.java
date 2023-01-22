package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DictionaryManager {
    Map<String,Dictionary> dictionaryMap;
    static DictionaryManager dm=null;
    DictionaryManager(){
        dictionaryMap = new HashMap<>();
    }
    public static DictionaryManager get(){
        if(dm == null)
            dm = new DictionaryManager();
        return dm;
    }

    public int getSize() {
        return dictionaryMap.size();
    }

    public boolean query(String... args) {
        int found = 0;
        String wordToSearch = args[args.length-1];
        for (String BookName : args){
            if(Objects.equals(BookName, wordToSearch))
                break;
            if(dictionaryMap.containsKey(BookName)){
                if(dictionaryMap.get(BookName).query(wordToSearch))
                    found ++;
            }
            else{
                Dictionary newD = new Dictionary(BookName);
                dictionaryMap.put(BookName,newD);
                if(newD.query(wordToSearch))
                    found++;
            }
        }
        return found!=0;
    }

    public boolean challenge(String... args) {
        int found = 0;
        String wordToSearch = args[args.length-1];
        for (String BookName : args){
            if(Objects.equals(BookName, wordToSearch))
                break;
            if(dictionaryMap.containsKey(BookName)){
                if(dictionaryMap.get(BookName).challenge(wordToSearch))
                    found ++;
            }
            else{
                Dictionary newD = new Dictionary(BookName);
                dictionaryMap.put(BookName,newD);
                if(newD.challenge(wordToSearch))
                    found++;
            }
        }
        return found!=0;
    }
}
