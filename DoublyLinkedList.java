package cs2321;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.datastructures.Position;
import net.datastructures.PositionalList;

/*
 * Alec Trent
 * Assignment 1
 * This class contains methods for a DoublyLinkedList using generics and implementing a Positional List.
 */

public class DoublyLinkedList<E> implements PositionalList<E> {
	//---------------- nested Node class ----------------
	/**
	 * Node of a doubly linked list, which stores a reference to its
	 * element and to both the previous and next node in the list.
	 */
	private static class Node<E> implements Position<E> {

		/** The element stored at this node */
		private E element;               // reference to the element stored at this node

		/** A reference to the preceding node in the list */
		private Node<E> prev;            // reference to the previous node in the list

		/** A reference to the subsequent node in the list */
		private Node<E> next;            // reference to the subsequent node in the list

		/**
		 * Creates a node with the given element and next node.
		 *
		 * @param e  the element to be stored
		 * @param p  reference to a node that should precede the new node
		 * @param n  reference to a node that should follow the new node
		 */
		public Node(E e, Node<E> p, Node<E> n) {
			element = e;
			prev = p;
			next = n;
		}

		public E getElement( ) throws IllegalStateException {
			if (next == null) // convention for defunct node
				throw new IllegalStateException("Position no longer valid");
			return element;
		}

		public Node<E> getPrev( ) {
			return prev;
		}

		public Node<E> getNext( ) {
			return next;
		}

		public void setElement(E e) {
			element = e;
		}

		public void setPrev(Node<E> p) {
			prev = p;		
		}
		public void setNext(Node<E> n) {
			next = n;
		}
	} //----------- end of nested Node class -----------

	// instance variables of the DoublyLinkedList
	/** Sentinel node at the beginning of the list */
	private Node<E> header;                    // header sentinel

	/** Sentinel node at the end of the list */
	private Node<E> trailer;                   // trailer sentinel

	/** Number of elements in the list (not including sentinels) */
	private int size = 0;                      // number of elements in the list

	/** Constructs a new empty list. */
	public DoublyLinkedList() {
		header = new Node<>(null, null, null);      // create header
		trailer = new Node<>(null, header, null);   // trailer is preceded by header
		header.setNext(trailer);                    // header is followed by trailer
	}

	/**
	 * Validates the nodes, ensuring a suitable node to be used
	 * 
	 * @param p
	 * @return
	 * @throws IllegalArgumentException
	 */
	private Node<E> validate(Position<E> p) throws IllegalArgumentException{
		if(!(p instanceof Node)) throw new IllegalArgumentException("Invalid p");
		Node<E> node = (Node<E>) p;

		if(node.getNext() == null)
			throw new IllegalArgumentException("p is no longer in the list");

		return node;
	}

	/**
	 * Returns the node in the specified position
	 * 
	 * @param node
	 * @return
	 */
	private Position<E> position(Node<E> node){
		if (node == header || node == trailer)
			return null;
		return node;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Position<E> first() {
		return position(header.getNext( ));
	}

	@Override
	public Position<E> last() {
		return position(trailer.getPrev( ));
	}

	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return position(node.getPrev( ));
	}

	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return position(node.getNext( ));
	}

	@Override
	public Position<E> addFirst(E e) {
		return addBetween(e, header, header.getNext( )); // just after the header
	}

	@Override
	public Position<E> addLast(E e) {
		return addBetween(e, trailer.getPrev( ), trailer);
	}

	@Override
	public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return addBetween(e, node.getPrev( ), node);
	}

	@Override
	public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return addBetween(e, node, node.getNext( ));

	}

	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E value = p.getElement();
		node.setElement(e);
		return value;
	}

	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		Node<E> predecessor = node.getPrev();
		Node<E> successor = node.getNext();
		predecessor.setNext(successor);
		successor.setPrev(predecessor);
		size--;
		E answer = node.getElement();
		node.setElement(null); 			// help with garbage collection
		node.setPrev(null);				// and convention for defunct node
		node.setNext(null);
		return answer;
	}

	/**
	 * Removes element from the beginning of the array
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	public E removeFirst() throws IllegalArgumentException {
		if (isEmpty()) return null;                  // nothing to remove
		return remove(header.getNext());             // first element is beyond header
	}

	/**
	 * Removes element from the end of the array
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	public E removeLast() throws IllegalArgumentException {
		if (isEmpty()) return null;                  // nothing to remove
		return remove(trailer.getPrev());            // last element is before trailer
	}

	/**
	 * Adds elements between two other nodes
	 * 
	 * @param e
	 * @param predecessor
	 * @param successor
	 * @return
	 */
	private Position<E> addBetween(E e, Node<E> predecessor, Node<E> successor) {
		// create and link a new node
		Node<E> newest = new Node<>(e, predecessor, successor);
		predecessor.setNext(newest);
		successor.setPrev(newest);
		size++;
		return newest;
	}

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator( );
	}

	@Override
	public Iterable<Position<E>> positions() {
		return new PositionIterable( ); // create a new instance of the inner class
	}

	//---------------- nested PositionIterator class ----------------
	private class PositionIterator implements Iterator<Position<E>> {
		private Position<E> cursor = first( );		// position of the next element to report
		private Position<E> recent = null;		 // position of last reported element
		// Tests whether the iterator has a next object.
		public boolean hasNext( ) { return (cursor != null); }
		// Returns the next position in the iterator.
		public Position<E> next( ) throws NoSuchElementException {
			if (cursor == null) throw new NoSuchElementException("nothing left");
			recent = cursor; 				// element at this position might later be removed
			cursor = after(cursor);
			return recent;
		}
		// Removes the element returned by most recent call to next.
		public void remove( ) throws IllegalStateException {
			if (recent == null) throw new IllegalStateException("nothing to remove");
			DoublyLinkedList.this.remove(recent); // remove from outer list
			recent = null; // do not allow remove again until next is called
		}
	} //------------ end of nested PositionIterator class ------------

	//---------------- nested PositionIterable class ----------------
	private class PositionIterable implements Iterable<Position<E>> {
		public Iterator<Position<E>> iterator( ) { return new PositionIterator( ); }
	} //------------ end of nested PositionIterable class ------------

	//---------------- nested ElementIterator class ----------------
	// This class adapts the iteration produced by positions() to return elements.
	private class ElementIterator implements Iterator<E> {
		Iterator<Position<E>> posIterator = new PositionIterator( );
		public boolean hasNext( ) { return posIterator.hasNext( ); }
		public E next( ) { return posIterator.next( ).getElement( ); } // return element!
		public void remove( ) { posIterator.remove( ); }
	}
}