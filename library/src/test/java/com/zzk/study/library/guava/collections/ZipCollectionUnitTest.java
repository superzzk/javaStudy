package com.zzk.study.library.guava.collections;

import com.google.common.collect.Streams;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class ZipCollectionUnitTest {

    private List<String> names;
    private List<Integer> ages;
    private List<String> expectedOutput;

    @Before
    public void setUp() throws Exception {
        names = Arrays.asList("John", "Jane", "Jack", "Dennis");
        ages = Arrays.asList(24, 25, 27);
        expectedOutput = Arrays.asList("John:24", "Jane:25", "Jack:27");
    }

    @Test
    public void zipCollectionUsingGuava21() {
        List<String> output = Streams
                .zip(names.stream(), ages.stream(), (name, age) -> name + ":" + age)
                .collect(Collectors.toList());

        assertEquals(output, expectedOutput);
    }

    @Test
    public void zipCollectionUsingIntStream() {
        List<String> output = IntStream
                .range(0, Math.min(names.size(), ages.size()))
                .mapToObj(i -> names.get(i) + ":" + ages.get(i))
                .collect(Collectors.toList());

        assertEquals(output, expectedOutput);
    }
}