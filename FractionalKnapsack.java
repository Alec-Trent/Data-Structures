package cs2321;

import cs2321.HeapPQ.PQEntry;
import cs2321.HeapPQ;
import net.datastructures.Entry;
import net.datastructures.PriorityQueue;

/**
 * @author: Alec Trent
 *
 */

import java.util.Comparator;

public class FractionalKnapsack {

	/**
	 * Goal: Choose items with maximum total benefit but with weight at most W.
	 *       You are allowed to take fractional amounts from items.
	 *       
	 * @param items items[i][0] is weight for item i
	 *              items[i][1] is benefit for item i
	 * @param knapsackWeight
	 * @return The maximum total benefit. Please use double type operation. For example 5/2 = 2.5
	 * 		 
	 */
	@TimeComplexity("O(n lg n)")
	public static double MaximumValue(int[][] items, int knapsackWeight) {
		MaxComparator<Double> comparator = new MaxComparator<>();
		PriorityQueue<Double, Integer> heap = new HeapPQ<>(comparator);
		/*
		 * TCJ
		 * for loop iterates n times, and insertion is log n
		 * while loop iterates n time
		 */
		for(int i=0; i<items.length; i++) // loops n times
			heap.insert((double) items[i][1]/items[i][0],items[i][0]);	//inserts value and weight log n times
		int bottleWeight = 0;	//total weight
		double bottleBenefit = 0; 
		while (bottleWeight < knapsackWeight && !heap.isEmpty()) { // loops n times
			Entry nextEntry = heap.removeMin(); // actually removes max
			Double value = (Double)nextEntry.getKey();
			Integer amount = (Integer)nextEntry.getValue();
			Integer remainingWeight = knapsackWeight - bottleWeight; //calculate how much to add to big bottle
			if(remainingWeight > amount) {
				bottleWeight += amount;
				bottleBenefit += value*amount;
			} else {
				bottleWeight += remainingWeight;
				bottleBenefit += value*remainingWeight;
			}
		}
		return bottleBenefit;
	}
}