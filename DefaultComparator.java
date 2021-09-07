package cs2321;

import java.util.Comparator;

public class DefaultComparator<K extends Comparable<K>> implements Comparator<K> {
    public int compare(K a, K b) throws ClassCastException {
        return a.compareTo(b);
    }
}