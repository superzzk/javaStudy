package zzk.study.java.core.util.concurrent;

import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

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
	public void test_merge() {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		map.put("X", "x");
		Assert.assertEquals("x", map.get("X"));

		map.merge("X", "x", (v1, v2) -> null);
		Assert.assertNull(map.get("X"));

		map.put("Y", "y");
		map.put("X", "x1");
		map.merge("X", "x1", (v1, v2) -> "z");
		Assert.assertEquals("z", map.get("X"));

		//如果map中没有，则直接使用value
		map.merge("Z", "z", (v1, v2) -> "sth");
		Assert.assertEquals("z", map.get("Z"));

		//BiFunction中传入的2个参数是新旧value
		map.merge("X", "x2", (v1, v2) -> v2.concat("z"));
		Assert.assertEquals("x2z", map.get("X"));
	}

	@Test
	public void test_compute() {
		ConcurrentHashMap<String, Integer> map1 = new ConcurrentHashMap<>();
		map1.put("A", 1);
		map1.put("B", 2);
		map1.put("C", 3);

		// Compute a new value for the existing key
		System.out.println("1st print => " + map1.compute("A",
				(k, v) -> v == null ? 42 : v + 40));
		System.out.println("2nd print => " + map1);
		// This will add a new (key, value) pair
		System.out.println("3rd print => " + map1.compute("X",
				(k, v) -> v == null ? 42 : v + 41));
		System.out.println("4th print => " + map1);

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
