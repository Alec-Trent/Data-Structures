package cs2321;

import net.datastructures.*;

/**
 * A lookup table using an ArrayList data structure.
 * <p>
 * Course: CS2321 Section R01
 * Assignment: #6
 *
 * @author Alec Trent
 */

@TimeComplexity("O(n)")
public class LookupTable<K extends Comparable<K>, V> extends AbstractMap<K, V> implements SortedMap<K, V> {

    private ArrayList<Entry<K, V>> table;
    int size = 0;

    //Returns the index of where entry was found, or where it should go.
    @TimeComplexity("O(1)")
    private int findIndex(K key) {
        int returnIndex;
        for (returnIndex = 0; returnIndex < this.size; returnIndex++) {
            if (table.get(returnIndex).getKey().compareTo(key) >= 0)
                return returnIndex;
        }
        return returnIndex; // special value denotes that key was not found
    }

    //Checks index for null values
    @TimeComplexity("O(1)")
    private boolean indexCheck(int index, K key) {
        if (safeEntry(index) == null)
            return false;
        return table.get(index).getKey().equals(key);
    }

    /**
     * Constructs an empty lookup table.
     */
    @TimeComplexity("O(1)")
    public LookupTable() {
        table = new ArrayList<>();
    }

    /**
     * Returns the total number of entries in the lookup table.
     */
    @Override
    @TimeComplexity("O(1)")
    public int size() {
        return this.size;
    }

    /**
     * Returns whether the lookup table is empty.
     */
    @Override
    @TimeComplexity("O(1)")
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns a (possibly empty) iteration of all values associated with the key.
     */
    @Override
    @TimeComplexity("O(1)")
    public V get(K key) {
        int index = findIndex(key);
        if (!indexCheck(index, key)) {
            return null;
        } else {
            return table.get(index).getValue();
        }
    }

    /**
     * Adds a new entry associating key with value.
     */
    @Override
    @TimeComplexity("O(1)")
    public V put(K key, V value) {
        int index = findIndex(key);
        Entry<K, V> newEntry = new mapEntry<>(key, value);
        if (!indexCheck(index, key)) {
            table.add(index, newEntry);
            this.size++;
            return null;
        } else {
            V oldValue = table.get(index).getValue();
            table.set(index, newEntry);
            return oldValue;
        }
    }

    /**
     * Removes the (key,value) entry, if it exists.
     */
    @Override
    @TimeComplexity("O(1)")
    public V remove(K key) {
        int index = findIndex(key);
        if (!indexCheck(index, key)) {
            return null; // no match
        } else {
            this.size--;
            return table.remove(index).getValue();
        }
    }

    /**
     * Returns an iteration of all entries in the lookup table.
     */
    @Override
    @TimeComplexity("O(n)")
    public Iterable<Entry<K, V>> entrySet() {
         /*
        TCJ
        call to snapshot
         */
        return snapshot(0, null);
    }

    //Utility returns the entry at index j, or else null if j is out of bounds.
    @TimeComplexity("O(1)")
    private Entry<K, V> safeEntry(int j) {
        if (j < 0 || j >= table.size()) return null;
        return table.get(j);
    }

    //Returns the entry having the least key (or null if map is empty)
    @Override
    @TimeComplexity("O(1)")
    public Entry<K, V> firstEntry() {
        return safeEntry(0);
    }

    //Returns the entry having the greatest key (or null if map is empty)
    @Override
    @TimeComplexity("O(1)")
    public Entry<K, V> lastEntry() {
        return safeEntry(table.size() - 1);
    }

    //Returns the entry with least key greater than or equal to given key (if any).
    @Override
    @TimeComplexity("O(1)")
    public Entry<K, V> ceilingEntry(K key) {
        return safeEntry(findIndex(key));
    }

    //Returns the entry with greatest key less than or equal to given key (if any)
    @Override
    @TimeComplexity("O(n)")
    public Entry<K, V> floorEntry(K key) {
        /*
        loops through all data
         */
        int j = findIndex(key);
        if (j == size() || !key.equals(table.get(j).getKey()))
            j--; // look one earlier (unless we had found a perfect match)
        return safeEntry(j);
    }

    //Returns the entry with greatest key strictly less than given key (if any).
    @Override
    @TimeComplexity("O(1)")
    public Entry<K, V> lowerEntry(K key) {
        return safeEntry(findIndex(key) - 1); // go strictly before the ceiling entry
    }

    //Returns the entry with least key strictly greater than given key (if any).
    @Override
    @TimeComplexity("O(n)")
    public Entry<K, V> higherEntry(K key) {
        /*
        Loops through data
         */
        int j = findIndex(key);
        if (j < size() && key.equals(table.get(j).getKey()))
            j++; // go past exact match
        return safeEntry(j);
    }

    // support for snapshot iterators for entrySet() and subMap() follow
    @TimeComplexity("O(n)")
    private Iterable<Entry<K, V>> snapshot(int startIndex, K stop) {
        /*
        TCJ
        While loop O(n)
         */
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        int j = startIndex;
        while (j < table.size() && (stop == null || stop.compareTo(table.get(j).getKey()) > 0))
            buffer.addLast(table.get(j++));
        return buffer;
    }

    @Override
    @TimeComplexity("O(n)")
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) {
        /*
        TCJ
        call to snapshot
         */
        return snapshot(findIndex(fromKey), toKey);
    }
}