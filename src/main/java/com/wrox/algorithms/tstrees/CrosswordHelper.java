package com.wrox.algorithms.tstrees;

import com.wrox.algorithms.iteration.Iterator;
import com.wrox.algorithms.lists.LinkedList;
import com.wrox.algorithms.lists.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class CrosswordHelper {
    private CrosswordHelper() {
    }
    public static void main(String[] args) throws IOException {
        assert args != null : "args can't be null";
        if (args.length < 2) {
            System.out.println("Usage CrosswordHelper <word-list> <pattern> [repetitions]");
            System.exit(-1);
        }
        int repetitions = 1;
        if (args.length > 2) {
            repetitions = Integer.parseInt(args[2]);
        }
        searchForPattern(loadWords(args[0]), args[1], repetitions);
    }
    private static void searchForPattern(TernarySearchTree tree, String pattern,
                                         int repetitions) {
        assert tree != null : "tree can't be null";
        System.out.println("Searching for pattern '" + pattern + "'..." +
                repetitions + " times");
        List words = null;
        for (int i = 0; i < repetitions; ++i) {
            words = new LinkedList();
            tree.patternMatch(pattern, words);
        }
        Iterator iterator = words.iterator();
        for (iterator.first(); !iterator.isDone(); iterator.next()) {
            System.out.println(iterator.current());
        }
    }
    private static TernarySearchTree loadWords(String fileName) throws IOException
    {
        TernarySearchTree tree = new TernarySearchTree();
        System.out.println("Loading words from '" + fileName + "'...");
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        try {
            String word;
            while ((word = reader.readLine()) != null) {
                tree.add(word);
            }
        } finally {
            reader.close();
        }
        return tree;
    }
}