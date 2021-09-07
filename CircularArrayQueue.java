package cs2321;

import net.datastructures.Queue;

/*
 * Alec Trent
 * Assignment 1
 * This class contains methods for a CircularArrayQueue using generics and implementing a Queue.
 */

public class CircularArrayQueue<E> implements Queue<E> {
	// instance variables
	/** Default array capacity. */
	public static final int CAPACITY = 1000;      // default array capacity

	/** Generic array used for storage of queue elements. */
	private E[] data;                             // generic array used for storage

	/** Index of the top element of the queue in the array. */
	private int f = 0;                            // index of the front element

	/** Current number of elements in the queue. */
	private int size = 0;                           // current number of elements

	// constructors
	/** Constructs an empty queue using the default array capacity. */
	public CircularArrayQueue() {this(CAPACITY);}         // constructs queue with default capacity

	/**
	 * Constructs and empty queue with the given array capacity.
	 * @param capacity length of the underlying array
	 */
	@SuppressWarnings({"unchecked"})
	public CircularArrayQueue(int queueSize) {             // constructs queue with given capacity
		data = (E[]) new Object[queueSize];         		 // safe cast; compiler may give warning
	}

	@Override
	public int size() { return size; }

	@Override
	public boolean isEmpty() { return size == 0; }

	@Override
	public void enqueue(E e) {
		if (size == data.length) throw new IllegalStateException("Queue is full");
		int avail = (f + size) % data.length;   	// use modular arithmetic
		data[avail] = e;
		size++;
	}

	@Override
	public E first() {
		if (isEmpty()) return null;
		return data[f];
	}

	@Override
	public E dequeue() {
		if (isEmpty()) return null;
		E answer = data[f];
		data[f] = null;                             // dereference to help garbage collection
		f = (f + 1) % data.length;					// use modular arithmetic
		size--;
		return answer;
	}
}