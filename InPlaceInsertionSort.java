package cs2321;
/**
 * @author: Alec Trent
 *
 */
public class InPlaceInsertionSort<K extends Comparable<K>> implements Sorter<K> {
	/**
	 * sort - Perform an in-place insertion sort
	 * @param array - Array to sort
	 */
	@TimeComplexity("O(n^2)")
	public void sort(K[] array) {
		/*
		 * TCJ
		 * for loop n iterations and 
		 *  summation of n-1 
		 *  makes n^2
		 */
		for(int i=0; i<= array.length-1; i++) {	// n iterations
			K curr = array[i];	// n-1 iterations
			int j = i-1;
			while(j>=0 && array[j].compareTo(curr) > 0) {	// n-1 iterations
				array[j+1] = array[j];
				j--;
			}
			array[j+1] = curr;	// n-1 iterations
		}
	}
}