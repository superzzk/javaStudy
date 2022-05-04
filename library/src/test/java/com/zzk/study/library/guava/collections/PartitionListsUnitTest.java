package com.zzk.study.library.guava.collections;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.ListUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PartitionListsUnitTest {



    //partition
    @Test
    public void whenPartitionList_thenPartitioned() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom", "Viki", "Tyler");
        final List<List<String>> result = Lists.partition(names, 2);

        assertEquals(3, result.size());
        assertThat(result.get(0), contains("John", "Jane"));
        assertThat(result.get(1), contains("Adam", "Tom"));
        assertThat(result.get(2), contains("Viki", "Tyler"));
    }

    @Test
    public final void givenListPartitioned_whenOriginalListIsModified_thenPartitionsChangeAsWell() {
        // Given
        final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
        final List<List<Integer>> subSets = Lists.partition(intList, 3);

        // When
        intList.add(9);
        final List<Integer> lastPartition = subSets.get(2);
        final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8, 9);
        assertThat(lastPartition, equalTo(expectedLastPartition));
    }

    //partition with apache commons
    @Test
    public final void givenListPartitioned_whenOriginalListIsModified_thenPartitionsChange() {
        // Given
        final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
        final List<List<Integer>> subSets = ListUtils.partition(intList, 3);

        // When
        intList.add(9);
        final List<Integer> lastPartition = subSets.get(2);
        final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8, 9);
        assertThat(lastPartition, equalTo(expectedLastPartition));
    }

    // java8 groupBy
    @Test
    public final void givenList_whenParitioningIntoNSublistsUsingGroupingBy_thenCorrect() {
        final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

        final Map<Integer, List<Integer>> groups = intList.stream().collect(Collectors.groupingBy(s -> (s - 1) / 3));
        final List<List<Integer>> subSets = new ArrayList<List<Integer>>(groups.values());

        // When
        final List<Integer> lastPartition = subSets.get(2);
        final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8);
        assertThat(subSets.size(), equalTo(3));
        assertThat(lastPartition, equalTo(expectedLastPartition));

        // intList.add(9);
        // System.out.println(groups.values());
    }

    // java8 partitionBy
    @Test
    public final void givenList_whenParitioningIntoSublistsUsingPartitionBy_thenCorrect() {
        final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

        final Map<Boolean, List<Integer>> groups = intList.stream().collect(Collectors.partitioningBy(s -> s > 6));
        final List<List<Integer>> subSets = new ArrayList<List<Integer>>(groups.values());

        // When
        final List<Integer> lastPartition = subSets.get(1);
        final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8);
        assertThat(subSets.size(), equalTo(2));
        assertThat(lastPartition, equalTo(expectedLastPartition));

        // intList.add(9);
        // System.out.println(groups.values());
    }

    // java8 split by separator
    @Test
    public final void givenList_whenSplittingBySeparator_thenCorrect() {
        final List<Integer> intList = Lists.newArrayList(1, 2, 3, 0, 4, 5, 6, 0, 7, 8);

        final int[] indexes = Stream.of(IntStream.of(-1), IntStream.range(0, intList.size()).filter(i -> intList.get(i) == 0), IntStream.of(intList.size())).flatMapToInt(s -> s).toArray();
        final List<List<Integer>> subSets = IntStream.range(0, indexes.length - 1).mapToObj(i -> intList.subList(indexes[i] + 1, indexes[i + 1])).collect(Collectors.toList());

        // When
        final List<Integer> lastPartition = subSets.get(2);
        final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8);
        assertThat(subSets.size(), equalTo(3));
        assertThat(lastPartition, equalTo(expectedLastPartition));
    }

}