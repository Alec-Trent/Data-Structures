package cs2321;

import java.util.Comparator;

public class MaxComparator<K> implements Comparator<K> {
	public int compare(K a, K b) throws ClassCastException {
		return ((Comparable <K>) b).compareTo(a);
	}
}