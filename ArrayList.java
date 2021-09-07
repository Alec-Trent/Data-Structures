package cs2321;

import java.util.Iterator;

import net.datastructures.List;

/*
 * Alec Trent
 * Assignment 1
 * This class contains methods for an ArrayList using generics and implementing a List.
 */

public class ArrayList<E> implements List<E> {

	/** Default array capacity. */
	public static final int DEFAULT_CAPACITY=16;     // default array capacity

	/** Generic array used for storage of list elements. */
	private E[] data;                        // generic array used for storage

	/** Current number of elements in the list. */
	private int size = 0;                    // current number of elements
	private int capacity;

	// constructors
	/** Creates an array list with default initial capacity. */
	public ArrayList() { this(DEFAULT_CAPACITY); }   // constructs list with default capacity

	/** Creates an array list with given initial capacity. */
	public ArrayList(int capacity) {         // constructs list with given capacity
		this.capacity = capacity;
		data = (E[]) new Object[capacity];     // safe cast; compiler may give warning
	}

	@Override
	public int size() { return size; }

	@Override
	public boolean isEmpty() { return size == 0; }

	@Override
	public E get(int i) throws IndexOutOfBoundsException {
		checkIndex(i, size);
		return data[i];
	}

	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		checkIndex(i, size);
		E temp = data[i];
		data[i] = e;
		return temp;
	}

	@Override
	public void add(int i, E e) throws IndexOutOfBoundsException {
		checkIndex(i, size + 1);
		if (size == capacity)               // not enough capacity
			resize();             // so double the current capacity
		for (int k=size-1; k >= i; k--)        // start by shifting rightmost
			data[k+1] = data[k];
		data[i] = e;                           // ready to place the new element
		size++;
	}

	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		checkIndex(i, size);
		E temp = data[i];
		for (int k=i; k < size-1; k++)         // shift elements to fill hole
			data[k] = data[k+1];
		data[size-1] = null;                   // help garbage collection
		size--;
		return temp;
	}

	@Override
	public Iterator<E> iterator() {
		return new ArrayIterator();     // create a new instance of the inner class
	}
	/**
	 * Adds element to the beginning of the array
	 * 
	 * @param e
	 */
	public void addFirst(E e)  {
		add(0, e);
	}
	/**
	 * Adds element to the end of the array
	 * 
	 * @param e
	 */
	public void addLast(E e)  {
		add(size, e);
	}
	/**
	 * Removes element from the beginning of the array
	 * 
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public E removeFirst() throws IndexOutOfBoundsException {
		checkIndex(0, size); 
		E temp = data[0];
		size--;
		return temp;
	}
	/**
	 * 

	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public E removeLast() throws IndexOutOfBoundsException {
		checkIndex(0, size); 
		E temp = data[size-1];
		size--;
		return temp;
	}

	// Return the capacity of array, not the number of elements.
	// Notes: The initial capacity is 16. When the array is full, the array should be doubled. 
	public int capacity() {
		return capacity;
	}

	/** Checks whether the given index is in the range [0, n-1]. */
	protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
		if (i < 0 || i >= n)
			throw new IndexOutOfBoundsException("Illegal index: " + i);
	}

	/** Resizes internal array to have given capacity >= size. */
	protected void resize( ) {
		capacity = 2 * capacity;
		E[] temp = (E[]) new Object[capacity];     // safe cast; compiler may give warning
		for (int k=0; k < size; k++)
			temp[k] = data[k];
		data = temp;                               // start using the new array
	}

	//---------------- nested ArrayIterator class ----------------
	/**
	 * A (nonstatic) inner class. Note well that each instance contains an implicit
	 * reference to the containing list, allowing it to access the list's members.
	 */
	private class ArrayIterator implements Iterator<E> {
		/** Index of the next element to report. */
		private int j = 0;                   // index of the next element to report
		private boolean removable = false;   // can remove be called at this time?

		/**
		 * Tests whether the iterator has a next object.
		 * @return true if there are further objects, false otherwise
		 */
		public boolean hasNext() { return j < size; }   // size is field of outer instance

		/**
		 * Returns the next object in the iterator.
		 *
		 * @return next object
		 * @throws NoSuchElementException if there are no further elements
		 */
		public E next() {
			if (j == size) 
				removable = true;   // this element can subsequently be removed
			return data[j++];   // post-increment j, so it is ready for future call to next
		}

		/**
		 * Removes the element returned by most recent call to next.
		 * @throws IllegalStateException if next has not yet been called
		 * @throws IllegalStateException if remove was already called since recent next
		 */
		public void remove() throws IllegalStateException {
			if (!removable) throw new IllegalStateException("nothing to remove");
			ArrayList.this.remove(j-1);  // that was the last one returned
			j--;                         // next element has shifted one cell to the left
			removable = false;           // do not allow remove again until next is called
		}
	} //------------ end of nested ArrayIterator class ------------
}