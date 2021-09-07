package cs2321;

import net.datastructures.Entry;
import net.datastructures.Map;

/**
 * An unordered map using an ArrayList data structure.
 * <p>
 * Course: CS2321 Section R01
 * Assignment: #6
 *
 * @author Alec Trent
 */

@TimeComplexity("O(n)")
public class UnorderedMap<K, V> extends AbstractMap<K, V> {
    private ArrayList<Entry<K, V>> table;

    /**
     * Constructs an empty unordered map.
     */
    @TimeComplexity("O(1)")
    public UnorderedMap() {
        table = new ArrayList<>();
    }

    /**
     * Returns the total number of entries in the unordered map.
     */
    @TimeComplexity("O(1)")
    @Override
    public int size() {
        return table.size();
    }

    /**
     * Returns whether the unordered map is empty.
     */
    @TimeComplexity("O(1)")
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    //Returns the index of an entry with equal key, or −1 if none found.
    @TimeComplexity("O(n)")
    private int findIndex(K key) {
        /*
        TCJ
        For loop runs through all possible choices
         */
        int n = table.size();
        for (int j = 0; j < n; j++) {
            if (table.get(j).getKey().equals(key))
                return j;
        }
        return -1; // special value denotes that key was not found
    }

    /**
     * Returns a (possibly empty) iteration of all values associated with the key.
     */
    @Override
    @TimeComplexity("O(n)")
    public V get(K key) {
        /*
        TCJ
        call to findIndex
         */
        int index = findIndex(key);
        if (index == -1) {
            return null;
        } else {
            return table.get(index).getValue();
        }
    }

    /**
     * Adds a new entry associating key with value.
     */
    @Override
    @TimeComplexity("O(n)")
    public V put(K key, V value) {
        /*
        TCJ
        Call to findIndex
        addLast is O(1)
         */
        Entry<K, V> newEntry = new mapEntry<>(key, value);
        int index = findIndex(key);
        if (index == -1) {
            table.addLast(newEntry);
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
    @TimeComplexity("O(n)")
    public V remove(K key) {
        /*
        TCJ
        Call to findIndex
         */
        int index = findIndex(key);
        if (index == -1) {
            return null;
        } else {
            V oldValue = table.get(index).getValue();
            table.remove(index);
            return oldValue;
        }
    }

    /**
     * Returns an iteration of all entries in the unordered map.
     */
    @Override
    @TimeComplexity("O(1)")
    public Iterable<Entry<K, V>> entrySet() {
        return table;
    }
}