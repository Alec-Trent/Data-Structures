package cs2321;
/**
 * @author: Alec Trent
 *
 */
public class QuickSort<E extends Comparable<E>> implements Sorter<E> {
	@TimeComplexity ("O(n^2)")
	private void quickSort(E[] array, int index1, int index2) {
		/*
		 * TCJ
		 * Worse case it removes an element each iteration
		 *  therefore it runs n times
		 *  each iteration calls partition once 
		 *  partition has a time complexity of O(n)
		 */
		if(index1<index2) {	//
			int r = partition(array, index1, index2);
			quickSort(array, index1, r-1);
			quickSort(array, r+1, index2);
		}
	}
	@TimeComplexity("O(n)")
	private int partition(E[] array, int index1, int index2) {
		/*
		 * TCJ
		 * While loops average O(n)
		 */
		E pivot = array[index2];
		int i = index1;
		int j = index2 - 1;
		//While loops together O(n) 
		while(i<=j) {	// scans the array
			while(i<=j && array[i].compareTo(pivot) < 0) { // increments i, until index >= pivot
				i++;
			}
			while(i<=j && array[j].compareTo(pivot) > 0) {	// increments j until index <= pivot
				j--;
			}
			//O(1)
			if(i<=j) {	//	swaps and sets indexes
				swap(array, i,j);
				i++;
				j--;
			}
		}
		swap(array, i,index2);
		return i;
	}
	@TimeComplexity("O(1)")
	private void swap(E[] array, int index1, int index2) {
		E temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	@TimeComplexity("O(n^2)")
	public void sort(E[] array) {
		/*
		 * TCJ
		 * Quick sort and partition both average O(n)
		 */
		quickSort(array, 0, array.length-1);
	}
}