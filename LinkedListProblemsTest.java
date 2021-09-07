import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/*
 * Alec Trent
 * Assignment 0
 * This class contains JUnit testing for the LinkedListProblems class
 */

public class LinkedListProblemsTest {

	ListNode T;

	/**
	 * The common test data to be used is the list T: 1->1->2->3->3
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		int[] testdata= {1,1,2,3,3};
		this.T = new ListNode(testdata[0]);
		ListNode temp = this.T;

		for (int i=1; i<testdata.length;i++) {
			T.next= new ListNode(testdata[i]);;
			T = T.next;
		}
		this.T = temp;
	}

	/**
	 * 	Given a sorted linked list, delete all duplicates such that each element appear only once.
	 */
	@Test
	public void testDeleteDuplicates() {
		int[] expected = {1,2,3};
		ListNode newT=LinkedListProblems.deleteDuplicates(this.T);
		ListNode p=newT;

		for (int i=0;i<expected.length;i++) {
			assertEquals(expected[i], p.val);
			p=p.next;
		}
	}

	/**
	 * Reverse a singly linked list.
	 */
	@Test
	public void testReverseList() {
		int[] expected = {3,3,2,1,1};
		ListNode newT=LinkedListProblems.reverseList(T);
		ListNode p=newT;
		int i = 0;

		while (p!=null) {
			assertEquals(expected[i], p.val);
			p = p.next;
			i = i+1;
		}
	}
}