import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Alec Trent
 * Assignment 0
 * This class contains JUnit testing for the ArrayProblems class
 */

class ArrayProblemsTest {

	/**
	 * Runs before @Test
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * sortArray case 1
	 */
	@Test
	void testSortArray1() {
		int[] nums= {5,4,3,1};
		int[] expected = {1,3,4,5};
		assertArrayEquals(expected, ArrayProblems.sortArray(nums));
	}

	/**
	 * sortArray case 2
	 */
	@Test
	void testSortArray2() {
		int[] nums= {5,1,1,2,0,0};
		int[] expected = {0,0,1,1,2,5};
		assertArrayEquals(expected, ArrayProblems.sortArray(nums));
	}

	/**
	 * findKthLargest test case 1
	 */
	@Test
	void testFindKthLargest1() {
		int[] nums= {3,2,1,5,6,4};
		int k= 2;
		int expected = 5;
		assertEquals(expected, ArrayProblems.findKthLargest(nums, k));
	}

	/**
	 * findKthLargest test case 2
	 */
	@Test
	void testFindKthLargest2() {
		int[] nums= {3,2,3,1,2,4,5,5,6};
		int k= 4;
		int expected = 4;
		assertEquals(expected, ArrayProblems.findKthLargest(nums, k));
	}
}