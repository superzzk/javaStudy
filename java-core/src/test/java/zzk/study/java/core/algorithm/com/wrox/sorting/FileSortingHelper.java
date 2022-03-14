package zzk.study.java.core.algorithm.com.wrox.sorting;

import zzk.study.java.core.algorithm.com.wrox.iteration.Iterator;
import zzk.study.java.core.algorithm.com.wrox.lists.LinkedList;
import zzk.study.java.core.algorithm.com.wrox.lists.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class FileSortingHelper {
    private FileSortingHelper() {
    }
    public static void main(String[] args) throws Exception {
        sort(loadWords());
        System.err.println("Finished...press CTRL-C to exit");
        Thread.sleep(100000);
    }
    private static void sort(List wordList) {
        assert wordList != null : "tree can’t be null";
        System.out.println("Starting sort...");
        Comparator comparator = ReverseStringComparator.INSTANCE;
        ListSorter sorter = new ShellsortListSorter(comparator);
        List sorted = sorter.sort(wordList);
        Iterator i = sorted.iterator();
        i.first();
        while (!i.isDone()) {
            System.out.println(i.current());
            i.next();
        }
    }
    private static List loadWords() throws IOException {
        List result = new LinkedList();
        BufferedReader reader = new BufferedReader(new
                InputStreamReader(System.in));
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
}