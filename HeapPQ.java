package cs2321;

import java.util.Comparator;

import net.datastructures.*;
/**
 * A Adaptable PriorityQueue based on an heap. 
 * 
 * Course: CS2321 Section R01
 * Assignment: #3
 * @author Alec Trent
 */

public class HeapPQ<K,V> implements AdaptablePriorityQueue<K,V> {
	ArrayList<PQEntry<K,V>> data = new ArrayList<>();
	Comparator comp = new DefaultComparator<>();
	
	  //---------------- nested PQEntry class ----------------
	  /**
	   * A concrete implementation of the Entry interface to be used within
	   * a PriorityQueue implementation.
	   */
	  protected static class PQEntry<K,V> implements Entry<K,V> {
	    private K k;  // key
	    private V v;  // value
	    private int i;

	    public PQEntry(K key, V value, int index) {
	      k = key;
	      v = value;
	      i = index;
	    }

	    // methods of the Entry interface
	    public K getKey() { return k; }
	    public V getValue() { return v; }
	    public int getIndex() { return i; }
	    
	    // utilities not exposed as part of the Entry interface
	    protected void setKey(K key) { k = key; }
	    protected void setValue(V value) { v = value; }
	    protected void setIndex(int index) { i = index; }
	  } //----------- end of nested PQEntry class -----------

	/* use default comparator, see DefaultComparator.java */
	public HeapPQ() {
		super();
	}

	/* use specified comparator */
	public HeapPQ(Comparator<K> c) {
		super();
		comp=c;
	}

	/* helper functions */
	int parent(int j) { return (j-1) / 2; }
	int left(int j) { return 2*j + 1; }
	int right(int j) { return 2*j + 2; }
	boolean hasLeft(int j) { return left(j) < data.size(); }
	boolean hasRight(int j) { return right(j) < data.size(); }
	
	 /** Exchanges the entries at indices i and j of the array list. */
	@TimeComplexity("O(1)")
	  void swap(int i, int j) {
	    PQEntry<K,V> temp = data.get(i);
	    data.set(i, data.get(j));
	    data.get(i).setIndex(i);	// update index
	    data.set(j, temp);
	    data.get(j).setIndex(j);	//update index
	    
	  }
  
	/* 
	 * Return the data array that is used to store entries  
	 * This method is purely for testing purpose of auto-grader
	 */
	//  @TimeComplexity("O(1)")
	//public Object[] data() {
	//	return data.get(data);
	//}

	/**
	 * The entry should be bubbled up to its appropriate position 
	 * @param int move the entry at index j higher if necessary, to restore the heap property
	 */
	@TimeComplexity("O(n)")
	public void upheap(int j){
		/*
		 * TCJ
		 * while loop with nested if makes this O(n)
		 */
		while (j>0) {	// while loop for j is larger than 0
			int p = parent(j);	// assign variable p for parent node j
			if( comp.compare(data.get(j).getKey(), data.get(p).getKey()) >= 0) {	// comparison between node j and its parent to 0
				break;
			}
			swap(j,p);	// swaps node j and its parent
			j=p;	// sets parent to j and loops until in order
		}
	}

	/**
	 * The entry should be bubbled down to its appropriate position 
	 * @param int move the entry at index j lower if necessary, to restore the heap property
	 */
	@TimeComplexity("O(n)")
	public void downheap(int j){
		/*
		 * TCJ
		 * while loop with nested if statements
		 */
		while(hasLeft(j)) {		// while there is a left index of j, loop
			int leftIndex = left(j);	// variable assignment for the left node of j
			int smallChildIndex = leftIndex;	// left takes precedence over right so it will be smaller
			if(hasRight(j)) {	// if there is a right index of j, loop
				int rightIndex = right(j);	// variable assignment for the right node of j
				if(comp.compare(data.get(leftIndex).getKey(), data.get(rightIndex).getKey()) > 0) {	// compares left and right index to zero
					smallChildIndex = rightIndex;	// if value is larger than 0 then the smallChild is = to rightIndex
				}
			}
			if(comp.compare(data.get(smallChildIndex).getKey(), data.get(j).getKey()) >= 0) { // compares smallChild and index j to zero
				break;
			}
			swap(j, smallChildIndex);	// swaps the nodes
			j = smallChildIndex;	// sets smallChildIndex to j and loops until in order
		}
	}

	@Override
	@TimeComplexity("O(1)")
	public int size() {
		return data.size();
	}

	@Override
	@TimeComplexity("O(1)")
	public boolean isEmpty() {
		return data.size() == 0;
	}

	@Override
	@TimeComplexity("O(lg n)")
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		/*	
		 * TCJ
		 * addLast is O(1)
		 * Call to upheap or downheap makes this O(lg n)
		 */
		PQEntry<K,V> element = new PQEntry<>(key, value, data.size());	// set new data equal to variable element
		data.addLast(element);		// add the new element to the end of the heap
		element.i=data.size()-1;
		upheap(data.size()-1);				// correct the order of the heap by calling upheap
		return element;
	}

	@Override
	@TimeComplexity("O(1)")
	public Entry<K, V> min() {
		if( data.isEmpty()) {
			return null;
		}
		return data.get(0);
	}

	@Override
	@TimeComplexity("O(n)")
	public Entry<K, V> removeMin() {
		/*
		 * TCJ
		 * removeMin not being in order makes this O(n)
		 */
		if( data.isEmpty()) {
			return null;
		}
		Entry<K,V> min = data.get(0);  // set root to variable min
		swap(0, data.size()-1);			// swap min and the last node
		data.remove(data.size()-1);		//remove the last node
		downheap(0);					// correct the order of the tree
		return min;
	}

	@Override
	@TimeComplexity("O(lg n)")
	public void remove(Entry<K, V> entry) throws IllegalArgumentException {
		/*
		 * TCJ
		 * call to upheap and downheap make this O(lg n)
		 */
		if(!(entry instanceof Entry)) throw new IllegalArgumentException("Invalid entry");	// casting
		PQEntry<K,V> ent = (PQEntry<K,V>) entry;
		data.remove(ent.getIndex());		// remove the node
		swap(ent.getIndex(), data.size()-1);
		upheap(ent.getIndex());		// restore heap order
		downheap(ent.getIndex());	// restore heap order
	}

	@Override
	@TimeComplexity("O(lg n)")
	public void replaceKey(Entry<K, V> entry, K key) throws IllegalArgumentException {
		/*
		 * TCJ
		 * call to upheap and downheap make this O(lg n)
		 */
		if(!(entry instanceof Entry)) throw new IllegalArgumentException("Invalid entry");	// casting
		PQEntry<K,V> ent = (PQEntry<K,V>) entry;
		ent.setKey(key);	// replaces key with new key
		upheap(ent.getIndex());		// restore heap order
		downheap(ent.getIndex());	// restore heap order
	}

	@Override
	@TimeComplexity("O(1)")
	public void replaceValue(Entry<K, V> entry, V value) throws IllegalArgumentException {
		if(!(entry instanceof Entry)) throw new IllegalArgumentException("Invalid entry");	// casting
		PQEntry<K,V> ent = (PQEntry<K,V>) entry;
		ent.setValue(value);	// sets new value
	}
}