package zzk.study.java.core.java8.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Reduction {
    /**
     * count, sum, average, max, min
     */
    @Test
    private void demo1(){
        String[] strings = "this is an array of strings".split(" ");
        long count = Arrays.stream(strings)
                .map(String::length)
                .count();
        System.out.println("There are " + count + " strings");

        int totalLength = Arrays.stream(strings)
                .mapToInt(String::length)
                .sum();
        System.out.println("The total length is " + totalLength);

        OptionalDouble ave = Arrays.stream(strings)
                .mapToInt(String::length)
                .average();
        System.out.println("The average length is " + ave);

        OptionalInt max = Arrays.stream(strings)
                .mapToInt(String::length)
                .max();
        OptionalInt min = Arrays.stream(strings)
                .mapToInt(String::length)
                .min();
        System.out.println("The max and min lengths are " + max + " and " + min);
    }

    @Test
    private void demo2(){
        /*
        * In the lambda expression, you can think of the first argument of the binary operator
            as an accumulator, and the second argument as the value of each element in the stream.
        * */
        int sum = IntStream.rangeClosed(1, 10)
                .reduce((x, y) -> x + y).orElse(0);
        System.out.println("The sum is " + sum);

        // Doubling the values during the sum, wrong
        int doubleSum = IntStream.rangeClosed(1, 10)
                .reduce((x, y) -> x + 2 * y).orElse(0);

        // Doubling the values during the sum, right
        // providing the initial value of zero for the accumulator x,
        int doubleSum2 = IntStream.rangeClosed(1, 10)
                .reduce(0, (x, y) -> x + 2 * y);

        int sum2 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .reduce(0, Integer::sum);

        Integer max = Stream.of(3, 1, 4, 1, 5, 9)
                .reduce(Integer.MIN_VALUE, Integer::max);
        System.out.println("The max value is " + max);

        String s = Stream.of("this", "is", "a", "list")
                .reduce("", String::concat);

        List<Book> books = new ArrayList<>();
        Book book1 = new Book(1, "Modern Java Recipes");
        Book book2 = new Book(2, "Making Java Groovy");
        Book book3 = new Book(3, "Gradle Recipes for Android");
        books.add(book1);
        books.add(book2);
        books.add(book3);
        HashMap<Integer, Book> bookMap = books.stream()
                .reduce(new HashMap<Integer, Book>(),
                        (map, book) -> {
                            map.put(book.getId(), book);
                            return map;
                        },
                        (map1, map2) -> {
                            map1.putAll(map2);
                            return map1;
                        });
        bookMap.forEach((k,v) -> System.out.println(k + ": " + v));
    }



    static class Book {
        private Integer id;
        private String title;

        public Book() {}

        public Book(Integer id, String title) {
            this.id = id;
            this.title = title;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
