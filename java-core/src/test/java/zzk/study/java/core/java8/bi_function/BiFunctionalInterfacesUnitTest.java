package zzk.study.java.core.java8.bi_function;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

public class BiFunctionalInterfacesUnitTest {

    @Test
    public void givenTwoLists_whenCombined() {
        List<String> list1 = Arrays.asList("a", "b", "c");
        List<Integer> list2 = Arrays.asList(1, 2, 3);

        List<String> result = new ArrayList<>();
        for (int i=0; i < list1.size(); i++) {
            result.add(list1.get(i) + list2.get(i));
        }

        assertThat(result).containsExactly("a1", "b2", "c3");
    }

    @Test
    public void givenTwoLists_whenCombinedWithGeneralPurposeCombiner() {
        List<String> list1 = Arrays.asList("a", "b", "c");
        List<Integer> list2 = Arrays.asList(1, 2, 3);

        List<String> result = listCombiner(list1, list2, (a, b) -> a + b);

        assertThat(result).containsExactly("a1", "b2", "c3");
    }

    private static <T, U, R> List<R> listCombiner(List<T> list1,
                                                  List<U> list2,
                                                  BiFunction<T, U, R> combiner) {
        List<R> result = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            result.add(combiner.apply(list1.get(i), list2.get(i)));
        }
        return result;
    }

    @Test
    public void givenTwoLists_whenComparedWithCombiningFunction() {
        List<Double> list1 = Arrays.asList(1.0d, 2.1d, 3.3d);
        List<Float> list2 = Arrays.asList(0.1f, 0.2f, 4f);

        // algorithm to determine if the value in list1 > value in list 2
        List<Boolean> result = listCombiner(list1, list2, (a, b) -> a > b);

        assertThat(result).containsExactly(true, true, false);
    }

    @Test
    public void givenTwoLists_whenComparedForEqualityByCombiningFunction() {
        List<Float> list1 = Arrays.asList(0.1f, 0.2f, 4f);
        List<Float> list2 = Arrays.asList(0.1f, 0.2f, 4f);

        List<Boolean> result = listCombiner(list1, list2, (a, b) -> a.equals(b));

        assertThat(result).containsExactly(true, true, true);
    }

    @Test
    public void givenTwoLists_whenComparedForEqualityByCombiningFunctionWithMethodReference() {
        List<Float> list1 = Arrays.asList(0.1f, 0.2f, 4f);
        List<Float> list2 = Arrays.asList(0.1f, 0.2f, 4f);

        List<Boolean> result = listCombiner(list1, list2, Float::equals);

        assertThat(result).containsExactly(true, true, true);
    }

    @Test
    public void givenTwoLists_whenComparedWithCombiningFunctionWithCompareTo() {
        List<Double> list1 = Arrays.asList(1.0d, 2.1d, 3.3d);
        List<Double> list2 = Arrays.asList(0.1d, 0.2d, 4d);

        List<Integer> result = listCombiner(list1, list2, Double::compareTo);

        assertThat(result).containsExactly(1, 1, -1);
    }

    /**
     * Allows you to to pass in a lambda or method reference and then
     * get access to the BiFunction it is meant to become
     */
    private static <T, U, R> BiFunction<T, U, R> asBiFunction(BiFunction<T, U, R> function) {
        return function;
    }

    @Test
    public void givenTwoLists_whenComparedWithCombiningFunctionWithComposedBiFunction() {
        List<Double> list1 = Arrays.asList(1.0d, 2.1d, 3.3d);
        List<Double> list2 = Arrays.asList(0.1d, 0.2d, 4d);

        List<Boolean> result = listCombiner(list1, list2,
                asBiFunction(Double::compareTo)
                    .andThen(i -> i > 0));

        assertThat(result).containsExactly(true, true, false);
    }
}
