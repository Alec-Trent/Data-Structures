package cs2321;
//import cs2321.HeapPQ.PQEntry;
/**
 * @author: Alec Trent
 *
 */
public class InPlaceSelectionSort<K extends Comparable<K>> implements Sorter<K> {
	/**
	 * sort - Perform an in-place selection sort
	 * @param array - Array to sort
	 */
	@TimeComplexity("O(n^2)")
	public void sort(K[] array) {
		/*
		 * TCJ
		 * nested for loops creates n^2
		 */
		for(int i=0; i<array.length-1; i++) { // element less than i are sorted //n iterations
			int minIndex = i;
			for(int j=i+1; j< array.length; j++) {	//n iterations
				if(array[j].compareTo(array[minIndex]) < 0)
					minIndex = j;
			}
			if(minIndex != i)
				swap(array, minIndex, i);
		} // elements less than i+1 are sorted
	}

	@TimeComplexity("O(1)")
	private void swap(K[] array, int index1, int index2) {
		K temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
}