package cs2321;

import net.datastructures.Stack;

/*
 * Alec Trent
 * Assignment 2
 * This class contains methods for a DLLStack using generics and implementing a Stack.
 */

public class DLLStack<E> implements Stack<E> {
	
	/** Number of elements in the stack */
	//private int size = 0;                      // number of elements in the stack
	
	private DoublyLinkedList<E> list = new DoublyLinkedList<>( ); // an empty list
	
	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	@Override
	public void push(E e) {
		list.addFirst(e);
	}

	@Override
	public E top() {
		return list.first().getElement();
	}

	@Override
	public E pop() {
		return list.removeFirst();
	}
}