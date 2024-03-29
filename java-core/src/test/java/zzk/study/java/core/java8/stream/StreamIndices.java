package zzk.study.java.core.java8.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamIndices {

    public static List<String> getEvenIndexedStrings(String[] names) {
        List<String> evenIndexedNames = IntStream.range(0, names.length)
            .filter(i -> i % 2 == 0)
            .mapToObj(i -> names[i])
            .collect(Collectors.toList());
        return evenIndexedNames;
    }


    public static List<String> getOddIndexedStrings(String[] names) {
        List<String> oddIndexedNames = IntStream.range(0, names.length)
            .filter(i -> i % 2 == 1)
            .mapToObj(i -> names[i])
            .collect(Collectors.toList());
        return oddIndexedNames;
    }

}