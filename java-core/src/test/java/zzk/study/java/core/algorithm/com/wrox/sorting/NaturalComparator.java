package zzk.study.java.core.algorithm.com.wrox.sorting;

public final class NaturalComparator implements Comparator {
    public static final NaturalComparator INSTANCE =
            new NaturalComparator();
    private NaturalComparator() {
    }
    public int compare(Object left, Object right) {
        assert left != null : "left can’t be null";
        return ((Comparable) left).compareTo(right);
    }
}