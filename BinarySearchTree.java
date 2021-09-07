package cs2321;

import net.datastructures.Entry;
import net.datastructures.Position;
import net.datastructures.SortedMap;

/**
 * A binary search tree using a linked binary tree data structure.
 * <p>
 * Course: CS2321 Section R01
 * Assignment: #6
 *
 * @author Alec Trent
 */

@TimeComplexity("O(1)")
public class BinarySearchTree<K extends Comparable<K>, V> extends AbstractMap<K, V> implements SortedMap<K, V> {

    /* all the data will be stored in tree*/
    LinkedBinaryTree<Entry<K, V>> tree;
    int size = 0;  //the number of entries (mappings)

    /*
     * default constructor
     */
    public BinarySearchTree() {
        tree = (LinkedBinaryTree<Entry<K, V>>) new LinkedBinaryTree();
    }

    /*
     * Return the tree. The purpose of this method is purely for testing.
     * You don't need to make any change. Just make sure to use object tree to store your entries.
     */
    public LinkedBinaryTree<Entry<K, V>> getTree() {
        return tree;
    }

    protected void set(Position<Entry<K, V>> p, Entry<K, V> e) {
        tree.set(p, e);
    }

    protected Position<Entry<K, V>> root() {
        return tree.root();
    }

    @TimeComplexity("O(1)")
    private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
        tree.set(p, entry); // store new entry at p
        tree.addLeft(p, null); // add new sentinel leaves as children
        tree.addRight(p, null);
    }

    //Returns the position in p's subtree having given key (or else the terminal leaf).
    @TimeComplexity("O(n)")
    private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
        /*
        TCJ
        traverse all elements
         */
        if (tree.isExternal(p))
            return p; // key not found; return the final leaf
        int comp = key.compareTo(p.getElement().getKey());
        if (comp == 0)
            return p; // key found; return its position
        else if (comp < 0)
            return treeSearch(tree.left(p), key); // search left subtree
        else
            return treeSearch(tree.right(p), key); // search right subtree
    }

    //Returns the position with the minimum key in subtree rooted at Position p.
    @TimeComplexity("O(n)")
    protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
        /*
        TCJ
        traverse all elements
        while loop
         */
        Position<Entry<K, V>> walk = p;
        while (tree.isInternal(walk))
            walk = tree.left(walk);
        return tree.parent(walk);              // we want the parent of the leaf
    }

    //Returns the position with the maximum key in subtree rooted at Position p.
    @TimeComplexity("O(n)")
    protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
        /*
        TCJ
        traverse all elements
        while loop
         */
        Position<Entry<K, V>> walk = p;
        while (tree.isInternal(walk))
            walk = tree.right(walk);
        return tree.parent(walk); // we want the parent of the leaf
    }

    //Returns the next largest node to the one removed
    @TimeComplexity("O(n)")
    public Position<Entry<K, V>> successor(Position<Entry<K, V>> p) {
        /*
        TCJ
        traverse all elements
        while loop
         */
        Position<Entry<K, V>> walk = p;
        walk = tree.right(walk);
        while (tree.isInternal(walk))
            walk = tree.left(walk);
        return tree.parent(walk); // we want the parent of the leaf
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
    @TimeComplexity("O(n)")
    public V get(K key) {
        /*
        TCJ
        traverse all elements
         */
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (tree.isExternal(p)) return null; // unsuccessful search
        return p.getElement().getValue(); // match found
    }

    //Adds a new entry associating key with value and returns the old value
    @Override
    @TimeComplexity("O(n)")
    public V put(K key, V value) {
        /*
        create new key
         */
        Entry<K, V> newEntry = new mapEntry<>(key, value);
        if (root() == null) {
            tree.addRoot(newEntry);
            expandExternal(tree.root(), newEntry);
            size++;
            return null;
        }
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (tree.isExternal(p)) { // key is new
            expandExternal(p, newEntry);
            size++;
            return null;
        } else { // replacing existing key
            V old = p.getElement().getValue();
            tree.set(p, newEntry);
            return old;
        }
    }

    //Removes the (key,value) entry, if it exists.
    @Override
    @TimeComplexity("O(n)")
    public V remove(K key) {
        /*
        TCJ
        traverse all elements
         */
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (tree.isExternal(p)) { // key not found
            return null;
        } else {
            V old = p.getElement().getValue();
            if (tree.isInternal(tree.left(p)) && tree.isInternal(tree.right(p))) { // both children are internal
                Position<Entry<K, V>> replacement = tree.isExternal(tree.right(p)) ? treeMax(tree.left(p)) : successor(p);
                tree.set(p, replacement.getElement());
                p = replacement;
            } // now p has at most one child that is an internal node
            Position<Entry<K, V>> leaf = (tree.isExternal(tree.left(p)) ? tree.left(p) : tree.right(p));
            tree.remove(leaf);
            tree.remove(p); // sib is promoted in p’s place
            size--;
            return old;
        }
    }

    //Returns an iterable collection of all key-value entries of the map.
    @Override
    @TimeComplexity("O(n)")
    public Iterable<Entry<K, V>> entrySet() {
        /*
        TCJ
        traverses all elements to return iterable collection
         */
        ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
        for (Position<Entry<K, V>> p : tree.positions())
            if (tree.isInternal(p)) buffer.addLast(p.getElement());
        return buffer;
    }

    @Override
    @TimeComplexity("O(n)")
    public Entry<K, V> firstEntry() {
        /*
        TCJ
        call to treeMin
         */
        if (isEmpty()) return null;
        return treeMin(root()).getElement();
    }

    //Returns the entry having the greatest key (or null if map is empty)
    @Override
    @TimeComplexity("O(n)")
    public Entry<K, V> lastEntry() {
        /*
        TCJ
        call to treeMax
         */
        if (isEmpty()) return null;
        return treeMax(root()).getElement();
    }

    @Override
    @TimeComplexity("O(n^2)")
    public Entry<K, V> ceilingEntry(K key) {
        /*
        TCJ
        while loop through data
         */
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (tree.isInternal(p)) return p.getElement();   // exact match
        while (!tree.isRoot(p)) {
            if (p == tree.left(tree.parent(p)))
                return tree.parent(p).getElement();          // parent has next greater key
            else
                p = tree.parent(p);
        }
        return null;                                // no such ceiling exists
    }

    //Returns the entry with greatest key less than or equal to given key (if any).
    @Override
    @TimeComplexity("O(n)")
    public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
        /*
        TCJ
        while loop through data
         */
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (tree.isInternal(p)) return p.getElement(); // exact match
        while (!tree.isRoot(p)) {
            if (p == tree.right(tree.parent(p)))
                return tree.parent(p).getElement(); // parent has next lesser key
            else
                p = tree.parent(p);
        }
        return null; // no such floor exists
    }

    //Returns the entry with greatest key strictly less than given key (if any).
    @TimeComplexity("O(n^2)")
    public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
        /*
        TCJ
        call to treeMax
        while loop through data
         */
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (tree.isInternal(p) && tree.isInternal(tree.left(p)))
            return treeMax(tree.left(p)).getElement(); // this is the predecessor to p
        // otherwise, we had failed search, or match with no left child
        while (!tree.isRoot(p)) {
            if (p == tree.right(tree.parent(p)))
                return tree.parent(p).getElement(); // parent has next lesser key
            else
                p = tree.parent(p);
        }
        return null; // no such lesser key exists
    }

    @Override
    @TimeComplexity("O(n^2)")
    public Entry<K, V> higherEntry(K key) {
        /*
        TCJ
        call to treeMin
        while loop through data
         */
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (tree.isInternal(p) && tree.isInternal(tree.right(p)))
            return treeMin(tree.right(p)).getElement();     // this is the successor to p
        // otherwise, we had failed search, or match with no right child
        while (!tree.isRoot(p)) {
            if (p == tree.left(tree.parent(p)))
                return tree.parent(p).getElement();           // parent has next lesser key
            else
                p = tree.parent(p);
        }
        return null;                                 // no such greater key exists
    }

    //Returns an iterable of entries with keys in range [fromKey, toKey).
    @Override
    @TimeComplexity("O(n)")
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) {
        /*
        TCJ
        call to subMapRecurse
         */
        ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
        if (fromKey.compareTo(toKey) < 0) // ensure that fromKey < toKey
            subMapRecurse(fromKey, toKey, root(), buffer);
        return buffer;
    }

    @TimeComplexity("O(n)")
    private void subMapRecurse(K fromKey, K toKey, Position<Entry<K, V>> p, ArrayList<Entry<K, V>> buffer) {
        /*
        rescursive call on isInternal
         */
        if (tree.isInternal(p))
            if (p.getElement().getKey().compareTo(fromKey) < 0)
                // p's key is less than fromKey, so any relevant entries are to the right
                subMapRecurse(fromKey, toKey, tree.right(p), buffer);
            else {
                subMapRecurse(fromKey, toKey, tree.left(p), buffer); // first consider left subtree
                if (p.getElement().getKey().compareTo(toKey) < 0) { // p is within range
                    buffer.addLast(p.getElement()); // so add it to buffer, and consider
                    subMapRecurse(fromKey, toKey, tree.right(p), buffer); // right subtree as well
                }
            }
    }
}