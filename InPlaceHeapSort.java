package cs2321;
/**
 * @author: Alec Trent
 *
 */
public class InPlaceHeapSort<K extends Comparable<K>> implements Sorter<K> {

	/**
	 * sort - Perform an in-place heap sort
	 * @param array - Array to sort
	 */
	@TimeComplexity("O(n log n)")
	public void sort(K[] array) {
		/*
		 * TCJ
		 *  2 O(n log n) for loops 
		 */
		int heapSize = array.length;
		for(int i=(array.length/2)-1; i>=0; i--) {	// n iterations
			maxHeapify(array, i, heapSize);	// log n function
		}
		for(int i=array.length-1; i>0; i--) { // n iterations
			swap(array, 0,i);
			heapSize--;
			maxHeapify(array, 0, heapSize);	// log n function
		}
	}
	@TimeComplexity("O(1)")
	private void swap(K[] array, int index1, int index2) {
		K temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	/* helper functions */
	int parent(int j) { return (j-1) / 2; }
	int left(int j) { return 2*j + 1; }
	int right(int j) { return 2*j + 2; }
	boolean hasLeft(int j, int heapSize) { return left(j) < heapSize; }
	boolean hasRight(int j, int heapSize) { return right(j) < heapSize; }

	@TimeComplexity("O(log n)")
	private void maxHeapify(K[] array, int root, int heapSize) {
		/*
		 * TCJ
		 * while loop with nested if statements
		 */
		while(hasLeft(root, heapSize)) {		// while there is a left index of j, loop
			int leftIndex = left(root);	// variable assignment for the left node of j
			int largeChildIndex = leftIndex;	// left takes precedence over right so it will be smaller
			if(hasRight(root, heapSize)) {	// if there is a right index of j, loop
				int rightIndex = right(root);	// variable assignment for the right node of j
				if(array[leftIndex].compareTo(array[rightIndex]) < 0 )	// compares left and right index to zero
					largeChildIndex = rightIndex;	// if value is larger than 0 then the smallChild is = to rightIndex
			}
			if(array[largeChildIndex].compareTo(array[root]) <= 0)// compares smallChild and index j to zero
				break;
			swap(array ,root, largeChildIndex);	// swaps the nodes
			root = largeChildIndex;
		}
	}
}