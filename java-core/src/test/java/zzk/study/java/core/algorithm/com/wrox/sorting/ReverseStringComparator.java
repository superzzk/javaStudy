package zzk.study.java.core.algorithm.com.wrox.sorting;

public final class ReverseStringComparator implements Comparator {
    public static final ReverseStringComparator INSTANCE = new
            ReverseStringComparator();
    private ReverseStringComparator() {
    }
    public int compare(Object left, Object right) throws ClassCastException {
        assert left != null : "left can’t be null";
        assert right != null : "right can’t be null";
        return reverse((String) left).compareTo(reverse((String) right));
    }
    private String reverse(String s) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            result.append(s.charAt(s.length() - 1 - i));
        }
        return result.toString();
    }
}