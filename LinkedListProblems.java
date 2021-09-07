/*
 * Alec Trent
 * Assignment 0
 * This class contains two methods. One to delete duplicates and the other to reverse a list
 */

public class LinkedListProblems {
	/**
	 * Given a sorted linked list, delete all duplicates such that each element
	 * appear only once.
	 * 
	 * @param head
	 * @return current
	 */
	public static ListNode deleteDuplicates(ListNode head) {
		//start at the head
		ListNode current = head;
		//while a node is not null or its next is not null continue
		while (current != null) {
			ListNode temp = current;
			//while two nodes are equal in value remove one
			while (temp != null && temp.val == current.val) {
				temp = temp.next;
			}
			//re-establish the link between nodes
			current.next = temp;
			current = current.next;
		}
		return head;
	}

	/**
	 * Reverse a singly linked list.
	 * @param head
	 * @return previous
	 */
	public static ListNode reverseList(ListNode head) {
		ListNode previous = null; 
		ListNode current = head; 
		ListNode next = null; 
		//while the current node is not null iterate through the list re-establishing links
		while (current != null) { 
			next = current.next; 
			current.next = previous; 
			previous = current; 
			current = next; 
		} 
		head = previous; 
		return previous;
	}
}