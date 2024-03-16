package com.zzk.study.library.algo.ahocorasick;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

public class MatchWords {

    @Test
    public void demo(){
        String[] words = {"one", "two", "three"};
        Trie trie = Trie.builder()
                .onlyWholeWords()
                .addKeywords(words)
                .build();
        final Collection<Emit> emits = trie.parseText("a sentence with one, and two, and three");
        emits.forEach(System.out::println);
    }

    public static boolean containsWordsAhoCorasick(String inputString, String[] words) {
        Trie trie = Trie.builder()
                .onlyWholeWords()
                .addKeywords(words)
                .build();

        Collection<Emit> emits = trie.parseText(inputString);
        emits.forEach(System.out::println);

        boolean found = true;
        for(String word : words) {
            boolean contains = Arrays.toString(emits.toArray()).contains(word);
            if (!contains) {
                found = false;
                break;
            }
        }

        return found;
    }

}
