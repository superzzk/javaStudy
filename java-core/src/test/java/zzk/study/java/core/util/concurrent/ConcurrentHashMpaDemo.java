package zzk.study.java.core.util.concurrent;

import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;

public class ConcurrentHashMpaDemo {

    ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<>();

    @Before
    public void setup() {
        hashMap.put("A", 1);
        hashMap.put("B", 2);
        hashMap.put("C", 3);
        hashMap.put("D", 4);
        hashMap.put("E", 5);
        hashMap.put("F", 6);
        hashMap.put("G", 7);
    }

    @Test
    public void test_getOrDefault() {
        Integer a = hashMap.getOrDefault("A", 100);
        System.out.println(a);
        Integer b = hashMap.getOrDefault("Z", 100);
        System.out.println(b);
    }

    @Test
    public void test_forEach_parallel() {
        hashMap.forEach(2, (k, v) ->
                System.out.println("key->" + k + "is related with value-> " + v + ", by thread->" + Thread.currentThread()));
    }

    @Test
    public void test_search() {
        String result = hashMap.search(1, (k, v) -> {
            System.out.println(Thread.currentThread().getName());
            if (k.equals("A"))
                return k + "-" + v;
            return null;
        });
        System.out.println("result => " + result);
    }

    @Test
    public void merge() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        // key exist, replace to null
        map.merge("key", "value", (v1, v2) -> null);
        Assert.assertNull(map.get("key"));

        // key exist, replace with new value
        map.put("key", "value");
        map.merge("key", "value", (v1, v2) -> "new value");
        assertEquals("new value", map.get("key"));

        // key not exist, add new key
        map.merge("key2", "value", (v1, v2) -> "new value");
        assertEquals("value", map.get("key2"));

        // key exist, BiFunction中传入的2个参数是新旧value
        map.put("key", "value");
        map.merge("key", "new value", (v1, v2) -> v1 + " " + v2);
        assertEquals("value new value", map.get("key"));
    }

    @Test
    public void compute() {
        ConcurrentHashMap<String, Integer> map1 = new ConcurrentHashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);
        map1.put("C", 3);

        // key exist, compute a new value
        map1.compute("A", (k, v) -> v == null ? 42 : v + 40);
        assertEquals(41, map1.get("A").intValue());

        // key not exist, add new key
        map1.compute("X", (k, v) -> v == null ? 42 : v + 41);
        assertEquals(42, map1.get("X").intValue());


        //computeIfPresent method
        System.out.println("5th print => " + map1.computeIfPresent("X", (k, v) -> v == null ? 42 : v + 10));
        System.out.println("6th print => " + map1);
        //computeIfAbsent method
        System.out.println("7th print => " + map1.computeIfAbsent("Y", (k) -> 90));
        System.out.println("8th print => " + map1);
    }

    @Test
    public void test_reduce() {
        ConcurrentHashMap<String, Integer> reducedMap = new ConcurrentHashMap<>();
        reducedMap.put("One", 1);
        reducedMap.put("Two", 2);
        reducedMap.put("Three", 3);
        System.out.println("reduce example => "
                + reducedMap.reduce(2,
                (k, v) -> v * 2,
                (total, elem) -> total + elem));

        System.out.println("reduceKeys example => "
                + reducedMap.reduceKeys(2,
                (key1, key2) -> key1.length() > key2.length() ? key1 + "-" + key2 : key2 + "-" + key1));

        System.out.println("reduceValues example => "
                + reducedMap.reduceValues(2,
                (v) -> v * 2,
                (value1, value2) -> value1 > value2 ? value1 - value2 : value2 - value1));

        System.out.println("After reduce => " + reducedMap);
    }
}
