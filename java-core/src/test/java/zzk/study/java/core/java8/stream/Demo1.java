package zzk.study.java.core.java8.stream;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Demo1 {

    @Test
    public void compare_java_7_and_8() {
        System.out.println("Using Java 7: ");

        // Count empty strings
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        System.out.println("List: " + strings);
        long count = getCountEmptyStringUsingJava7(strings);

        System.out.println("Empty Strings: " + count);
        count = getCountLength3UsingJava7(strings);

        System.out.println("Strings of length 3: " + count);

        //Eliminate empty string
        List<String> filtered = deleteEmptyStringsUsingJava7(strings);
        System.out.println("Filtered List: " + filtered);

        //Eliminate empty string and join using comma.
        String mergedString = getMergedStringUsingJava7(strings, ", ");
        System.out.println("Merged String: " + mergedString);


        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        //get list of square of distinct numbers
        List<Integer> squaresList = getSquares(numbers);
        System.out.println("Squares List: " + squaresList);


        List<Integer> integers = Arrays.asList(1, 2, 13, 4, 15, 6, 17, 8, 19);

        System.out.println("List: " + integers);
        System.out.println("Highest number in List : " + getMax(integers));
        System.out.println("Lowest number in List : " + getMin(integers));
        System.out.println("Sum of all numbers : " + getSum(integers));
        System.out.println("Average of all numbers : " + getAverage(integers));
        System.out.println("Random Numbers: ");

        //print ten random numbers
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt());
        }

        System.out.println("Using Java 8: ");
        System.out.println("List: " + strings);

        count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println("Empty Strings: " + count);

        count = strings.stream().filter(string -> string.length() == 3).count();
        System.out.println("Strings of length 3: " + count);

        filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println("Filtered List: " + filtered);

        mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("Merged String: " + mergedString);

        squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.println("Squares List: " + squaresList);
        System.out.println("List: " + integers);

        IntSummaryStatistics stats = integers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("Highest number in List : " + stats.getMax());
        System.out.println("Lowest number in List : " + stats.getMin());
        System.out.println("Sum of all numbers : " + stats.getSum());
        System.out.println("Average of all numbers : " + stats.getAverage());
        System.out.println("Random Numbers: ");

        random.ints().limit(10).sorted().forEach(System.out::println);

        //parallel processing
        count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println("Empty Strings: " + count);
    }

    @Test
    public void test_map() {
        List<String> myList = Stream.of("a", "b")
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        assertEquals(asList("A", "B"), myList);
    }

    @Test
    public void test_flatMap() {
        //Suppose we have a list of lists of type String.

        List<List<String>> nestedList = asList(
                asList("one:one"),
                asList("two:one", "two:two", "two:three"),
                asList("three:one", "three:two", "three:three", "three:four"));

        //In order to flatten this nested collection into a list of strings,
        // we can use forEach together with a Java 8 method reference:
        List<String> ls = new ArrayList<>();
        nestedList.forEach(ls::addAll);
        System.out.println(ls);

        //or
        ls = nestedList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(ls);
    }



    @Test
    public void iterable_to_stream() {
        Iterable<String> iterable = Arrays.asList("Testing", "Iterable", "conversion", "to", "Stream");
        Assert.assertNotNull(StreamSupport.stream(iterable.spliterator(), false));
    }

    @Test
    public void stream_use_twice_throw_exception() {
        Stream<String> stringStream = Stream.of("A", "B", "C", "D");
        Optional<String> result1 = stringStream.findAny();
        System.out.println(result1.get());
        Assertions.assertThrows(IllegalStateException.class, ()->stringStream.findFirst());
    }

    @Test
    public void givenStream_whenUsingSupplier_thenNoExceptionIsThrown() {
        try {
            Supplier<Stream<String>> streamSupplier = () -> Stream.of("A", "B", "C", "D");
            Optional<String> result1 = streamSupplier.get().findAny();
            System.out.println(result1.get()); // A
            Optional<String> result2 = streamSupplier.get().findFirst();
            System.out.println(result2.get()); // A
        } catch (IllegalStateException e) {
            fail();
        }
    }

    private static int getCountEmptyStringUsingJava7(List<String> strings) {
        int count = 0;
        for (String string : strings) {
            if (string.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    private static int getCountLength3UsingJava7(List<String> strings) {
        int count = 0;

        for (String string : strings) {

            if (string.length() == 3) {
                count++;
            }
        }
        return count;
    }

    private static List<String> deleteEmptyStringsUsingJava7(List<String> strings) {
        List<String> filteredList = new ArrayList<String>();
        for (String string : strings) {
            if (!string.isEmpty()) {
                filteredList.add(string);
            }
        }
        return filteredList;
    }

    private static String getMergedStringUsingJava7(List<String> strings, String separator) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : strings) {
            if (!string.isEmpty()) {
                stringBuilder.append(string);
                stringBuilder.append(separator);
            }
        }
        String mergedString = stringBuilder.toString();
        return mergedString.substring(0, mergedString.length() - 2);
    }

    private static List<Integer> getSquares(List<Integer> numbers) {
        List<Integer> squaresList = new ArrayList<Integer>();

        for (Integer number : numbers) {
            Integer square = new Integer(number.intValue() * number.intValue());

            if (!squaresList.contains(square)) {
                squaresList.add(square);
            }
        }
        return squaresList;
    }

    private static int getMax(List<Integer> numbers) {
        int max = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {

            Integer number = numbers.get(i);

            if (number.intValue() > max) {
                max = number.intValue();
            }
        }
        return max;
    }

    private static int getMin(List<Integer> numbers) {
        int min = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            Integer number = numbers.get(i);

            if (number.intValue() < min) {
                min = number.intValue();
            }
        }
        return min;
    }

    private static int getSum(List numbers) {
        int sum = (int) (numbers.get(0));

        for (int i = 1; i < numbers.size(); i++) {
            sum += (int) numbers.get(i);
        }
        return sum;
    }

    private static int getAverage(List<Integer> numbers) {
        return getSum(numbers) / numbers.size();
    }
}