package cs2321;
import java.util.Arrays;
/**
 * @author: Alec Trent
 *
 */
public class MergeSort<E extends Comparable<E>> implements Sorter<E> {
	@TimeComplexity("O(n)")
	void merge(E[] array1, E[] array2, E[] array) {
		/*
		 * TCJ
		 * worse case every element in array 1 is
		 * compared to every element in array 2
		 * making this O(n)
		 */
		int i = 0;
		int j = 0;
		int k = 0;
		while(i<array1.length && j<array2.length) {
			if(array1[i].compareTo(array2[j]) < 0) {
				array[k] = array1[i];
				i++;
			} else {
				array[k] = array2[j];
				j++;
			}
			k++;
		}
		while(i<array1.length) {
			array[k] = array1[i];
			i++;
			k++;
		}
		while(j<array2.length) {
			array[k] = array2[j];
			j++;
			k++;
		}
	}

	@TimeComplexity("O(n log n)")
	public void sort(E[] array) {
		/*
		 * TCJ
		 *  sort is log n and merge is n
		 *  making n log n
		 */
		if (array.length == 1)
			return;
		int mid = array.length/2;
		E[] left = Arrays.copyOfRange(array, 0, mid);
		E[] right = Arrays.copyOfRange(array, mid, array.length);
		sort(left);
		sort(right);
		merge(left, right, array); //O(n)
	}
}