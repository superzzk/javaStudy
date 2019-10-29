package com.wrox.algorithms.bsearch;

import com.wrox.algorithms.lists.List;
import com.wrox.algorithms.sorting.Comparator;

public class IterativeBinaryListSearcher implements ListSearcher {
    private final Comparator _comparator;
    public IterativeBinaryListSearcher(Comparator comparator) {
        assert comparator != null : "comparator can’t be null";
        _comparator = comparator;
    }
    public int search(List list, Object key) {
        assert list != null : "list can’t be null";
        int lowerIndex = 0;
        int upperIndex = list.size() - 1;
        while (lowerIndex <= upperIndex) {
            int index = lowerIndex + (upperIndex - lowerIndex) / 2;
            int cmp = _comparator.compare(key, list.get(index));
            if (cmp == 0) {
                return index;
            } else if (cmp < 0) {
                upperIndex = index - 1;
            } else {
                lowerIndex = index + 1;
            }
        }
        return -(lowerIndex + 1);
    }
}