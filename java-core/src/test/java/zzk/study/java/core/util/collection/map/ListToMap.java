package zzk.study.java.core.util.collection.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ListToMap {

    private List<Book> bookList;
    private ConvertToMap convertToMap = new ConvertToMap();

    @Before
    public void init() {
        bookList = new ArrayList<>();
        bookList.add(new Book("The Fellowship of the Ring", 1954, "0395489318"));
        bookList.add(new Book("The Two Towers", 1954, "0345339711"));
        bookList.add(new Book("The Return of the King", 1955, "0618129111"));
    }

    @Test
    public void whenConvertFromListToMap() {
        assertTrue(convertToMap.listToMap(bookList).size() == 3);
    }

    @Test(expected = IllegalStateException.class)
    public void whenMapHasDuplicateKey_without_merge_function_then_runtime_exception() {
        convertToMap.listToMapWithDupKeyError(bookList);
    }

    @Test
    public void whenMapHasDuplicateKeyThenMergeFunctionHandlesCollision() {
        Map<Integer, Book> booksByYear = convertToMap.listToMapWithDupKey(bookList);
        assertEquals(2, booksByYear.size());
        assertEquals("0395489318", booksByYear.get(1954).getIsbn());
    }

    @Test
    public void whenCreateConcurrentHashMap() {
        assertTrue(convertToMap.listToConcurrentMap(bookList) instanceof ConcurrentHashMap);
    }

    @Test
    public void whenMapisSorted() {
        assertTrue(convertToMap.listToSortedMap(bookList).firstKey().equals("The Fellowship of the Ring"));
    }

    public class ConvertToMap {
        public Map<String, String> listToMap(List<Book> books) {
            return books.stream().collect(Collectors.toMap(Book::getIsbn, Book::getName));
        }

        public Map<Integer, Book> listToMapWithDupKeyError(List<Book> books) {
            return books.stream().collect(Collectors.toMap(Book::getReleaseYear, Function.identity()));
        }

        public Map<Integer, Book> listToMapWithDupKey(List<Book> books) {
            return books.stream().collect(Collectors.toMap(Book::getReleaseYear, Function.identity(), (existing, replacement) -> existing));
        }

        public Map<Integer, Book> listToConcurrentMap(List<Book> books) {
            return books.stream().collect(Collectors.toMap(Book::getReleaseYear, Function.identity(), (o1, o2) -> o1, ConcurrentHashMap::new));
        }

        public TreeMap<String, Book> listToSortedMap(List<Book> books) {
            return books.stream()
                    .collect(Collectors.toMap(Book::getName, Function.identity(), (o1, o2) -> o1, TreeMap::new));
        }
    }

    @Data
    @AllArgsConstructor
    public class Book {
        private String name;
        private int releaseYear;
        private String isbn;
    }
}
