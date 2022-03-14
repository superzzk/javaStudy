package zzk.study.java.core.algorithm.com.wrox.sorting;

import zzk.study.java.core.algorithm.com.wrox.iteration.Iterator;
import zzk.study.java.core.algorithm.com.wrox.lists.ArrayList;
import zzk.study.java.core.algorithm.com.wrox.lists.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class OptimizedFileSortingHelper {
    private OptimizedFileSortingHelper() {
    }
    public static void main(String[] args) throws Exception {
        List words = loadWords();
        reverseAll(words);
        System.out.println("Starting sort...");
        Comparator comparator = NaturalComparator.INSTANCE;
        ListSorter sorter = new ShellsortListSorter(comparator);
        List sorted = sorter.sort(words);
        reverseAll(sorted);
        printAll(sorted);
        System.err.println("Finished...press CTRL-C to exit");
        Thread.sleep(100000);
    }
    private static List loadWords() throws IOException {
        List result = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String word;
            while ((word = reader.readLine()) != null) {
                result.add(word);
            }
        } finally {
            reader.close();
        }
        return result;
    }
    private static String reverse(String s) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            result.append(s.charAt(s.length() - 1 - i));
        }
        return result.toString();
    }
    private static void reverseAll(List words) {
        for (int i = 0; i < words.size(); ++i) {
            words.set(i, reverse((String) words.get(i)));
        }
    }
    private static void printAll(List stringList) {
        Iterator iterator = stringList.iterator();
        iterator.first();
        while (!iterator.isDone()) {
            String word = (String) iterator.current();
            System.out.println(word);
            iterator.next();
        }
    }
}