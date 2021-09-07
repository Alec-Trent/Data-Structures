import java.util.Arrays;

/*
 * Alec Trent
 * Assignment 0
 * This class contains two methods for arrays. One sorts the array and the other finds the largest Kth value.
 */

public class ArrayProblems {
	/**
	 * Given an array of integers nums, sort the array in ascending order.
	 * @param nums
	 * @return nums
	 */
	public static int[] sortArray(int[] nums) {
		//sorts the array
		Arrays.sort(nums);
		return nums;
	}

	/**
	 * Find the kth largest element in an unsorted array.
	 * @param nums
	 * @param k
	 * @return kthLargest
	 */
	public static int findKthLargest(int[] nums, int k) { 
		//sorts the array
		Arrays.sort(nums);
		//determines kth largest
		int kthLargest = nums.length - k;
		return nums[kthLargest];
	}
}