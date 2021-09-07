package cs2321;

/*
 * Alec Trent
 * Assignment 2
 * This class contains the order method for Josephus game.
 * Every kth element in the queue is eliminated (dequeued) until one element is left.
 */

public class Josephus {
	/**
	 * All persons sit in a circle. When we go around the circle, initially starting
	 * from the first person, then the second person, then the third... 
	 * we count 1,2,3,.., k-1. The next person, that is the k-th person is out. 
	 * Then we restart the counting from the next person, go around, the k-th person 
	 * is out. Keep going the same way, when there is only one person left, she/he 
	 * is the winner. 
	 *  
	 * @parameter persons  an array of string which contains all player names.
	 * @parameter k  an integer specifying the k-th person will be kicked out of the game
	 * @return return a doubly linked list in the order when the players were out of the game. 
	 *         the last one in the list is the winner.  
	 */
	public DoublyLinkedList<String> order(String[] persons, int k ) {
		CircularArrayQueue<String> queue = new CircularArrayQueue<>();
		DoublyLinkedList<String> list = new DoublyLinkedList<>();

		// populates the queue
		// Time Complexity: O(n)
		for ( int i = 0; i <= persons.length - 1; i++) {
			queue.enqueue(persons [i]);
		}
		// while loop running until queue size is equal to one
		// Time Complexity: O(n^2)
		while (queue.size() > 1 ) {
			for (int j = 0; j < k-1; j++) {		// for loop that dequeues and enqueues players not eliminated
				String player = queue.dequeue();	
				queue.enqueue(player);
			}
			// adds the eliminated to the DLL
			String eliminated = queue.dequeue();
			list.addLast(eliminated);
		}
		// adds the winner to the DLL
		String player = queue.dequeue();
		list.addLast(player);

		return list;
	}
}