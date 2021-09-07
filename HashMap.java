package cs2321;

import net.datastructures.*;


/**
 * A hash map implementing map.
 * <p>
 * Course: CS2321 Section R01
 * Assignment: #6
 *
 * @author Alec Trent
 */

@TimeComplexity("O(n^2)")
public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    private UnorderedMap<K, V>[] table;
    int size = 0;  // number of mappings(entries)
    int DefaultCapacity = 17; //The default hash table size
    int capacity = DefaultCapacity; // The size of the hash table.
    int numberOfEntries = 0;

    /* Maintain the load factor <= 0.75.
     * If the load factor is greater than 0.75,
     * then double the table, rehash the entries, and put then into new places.
     */
    double loadfactor = 0.75;

    /**
     * Constructor that takes a hash size
     *
     * @param hashtable size: the number of buckets to initialize
     */
    @TimeComplexity("O(n)")
    public HashMap(int hashtablesize) {
        /*
        TCJ
        for loop O(n)
         */
        capacity = hashtablesize;
        table = (UnorderedMap<K, V>[]) new UnorderedMap[capacity];
        for (int index = 0; index < capacity; index++) {
            table[index] = new UnorderedMap<>();
        }
    }

    /**
     * Constructor that takes no argument
     * Initialize the hash table with default hash table size: 17
     */
    @TimeComplexity("O(n)")
    public HashMap() {
        /*
        TCJ
        for loop O(n)
         */
        table = (UnorderedMap<K, V>[]) new UnorderedMap[capacity];
        for (int index = 0; index < capacity; index++) {
            table[index] = new UnorderedMap<>();
        }
    }

    //This method should be called by map an integer to the index range of the hash table
    @TimeComplexity("O(1)")
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    /*
     * The purpose of this method is for testing if the table was doubled when rehashing is needed.
     * Return the the size of the hash table.
     * It should be 17 initially, after the load factor is more than 0.75, it should be doubled.
     */
    @TimeComplexity("O(1)")
    public int tableSize() {
        return this.size;
    }

    @Override
    @TimeComplexity("O(1)")
    public int size() {
        return this.size;
    }

    @Override
    @TimeComplexity("O(1)")
    public boolean isEmpty() {
        return this.size == 0;
    }

    //Returns a (possibly empty) iteration of all values associated with the key.
    @Override
    @TimeComplexity("O(1)")
    public V get(K key) {
        return table[hash(key)].get(key);
    }

    //Adds a new entry associating key with value and returns the old value
    @Override
    @TimeComplexity("O(1)")
    public V put(K key, V value) {
        V oldValue = table[hash(key)].put(key, value);
        if (oldValue == null) {
            this.size++;
        }
        return oldValue;
    }

    //Removes the (key,value) entry, if it exists.
    @Override
    @TimeComplexity("O(1)")
    public V remove(K key) {
        V removedValue = table[hash(key)].remove(key);
        if (removedValue != null) {
        	this.size--;
        }
        return removedValue;
    }

    @Override
    @TimeComplexity("O(n^2)")
    public Iterable<Entry<K, V>> entrySet() {
        /*
        TCJ
        nested for loops = O(n^2)
         */
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        for (int hashEntry = 0; hashEntry < capacity; hashEntry++)
            if (table[hashEntry] != null)
                for (Entry<K, V> entry : table[hashEntry].entrySet())
                    buffer.addLast(entry);
        return buffer;
    }
}