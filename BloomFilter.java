package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.BitSet;

public class BloomFilter {
    private final BitSet bitset;
    private final MessageDigest[] hashFunctions;
    private final int Size;
    /*
    - `bitset` is the data structure that stores the filter
    - `hashFunctions` is an array that stores the hash functions that will be used to calculate hash values for elements
    - `Size` is the size of the filter, which determines the number of bits in the `bitset`
    */

    public BloomFilter(int size, String... algs) {
        // Initialize the BitSet and hash function array
        bitset = new BitSet(size);
        hashFunctions = new MessageDigest[algs.length];
        int i = 0;
        for (String h : algs) {
            try {
                hashFunctions[i] = MessageDigest.getInstance(h);
                i++;
            } catch (Exception e) {
                System.err.println("The Hash name is not Valid");
            }
        }
        Size = size;
    }
    /*
    - This is the constructor for the class, it takes in 2 parameters `size` and `algs`
    - It creates new bitset of the size passed and hash function array of the length of `algs`
    - It then goes through each value of algs array and tries to create an instance of a digest algorithm, if not valid it will give error
    - sets `Size` variable to the size passed
     */

    public void add(String s) {
        for (MessageDigest hash : hashFunctions) {
            bitset.set(getWordBit(s, hash));
        }
    }
    /*
    - This method takes in a string `s` and adds it to the filter
    - It iterates over each hash function in the hashFunctions array and sets the bit in the bitset corresponding to the index returned by the `getWordBit` method
     */

    public boolean contains(String s) {
        for (MessageDigest hash : hashFunctions) {
            if (!bitset.get(getWordBit(s, hash))) {
                return false;
            }
        }
        return true;
    }
    /*
    - This method takes in a string `s` and checks if it's present in the filter
    - It iterates over each hash function in the hashFunctions array and checks if the bit in the bitset corresponding to the index returned by the `getWordBit` method is set
    - If any bit is not set it returns `false`, otherwise it returns `true`
     */

    public int getWordBit(String word, MessageDigest MD) {
        byte[] bts = MD.digest(word.getBytes());
        BigInteger bigInt = new BigInteger(bts);
        return Math.abs(bigInt.intValue()) % Size;
    }
    /*
    - This method takes in a string `word` and a `MessageDigest` object `MD`
    - It first digest the word using the message digest object and converts it to bytes, then converts that bytes to bigInteger
    - it then returns the absolute value of the big integer mod size of the bitset
     */

    @Override
    public String toString() {
        StringBuilder biteString = new StringBuilder();
        for (int i = 0; i < bitset.length(); i++)
            biteString.append(bitset.get(i) ? "1" : "0");
        return biteString.toString();
    }
    /*
    - This method overrides the `toString` method of the `Object` class
    - It creates a new `StringBuilder` object called `biteString`
    - It iterates over the length of the `bitset` and for each index, it appends a '1' if the bit is set and '0' otherwise
    - It then returns the string representation of the filter
     */

}
