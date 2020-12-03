package com.zzk.study.util.collection;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class ConversionDemo {

	List<Animal> list = new ArrayList<>();

	@Before
	public void init() {
		Animal cat = new Animal(1, "Cat");
		list.add(cat);
		Animal dog = new Animal(2, "Dog");
		list.add(dog);
		Animal pig = new Animal(3, "Pig");
		list.add(pig);
		Animal cow = new Animal(4, "Cow");
		list.add(cow);
		Animal goat = new Animal(5, "Goat");
		list.add(goat);
	}

	@Test
	public void demo_list_to_map() {

		Map<Integer, Animal> map = convertListAfterJava8(list);

		assertThat(map.values(), containsInAnyOrder(list.toArray()));
	}

	@Test(expected = IllegalStateException.class)
	public void givenADupIdList_whenConvertAfterJava8_thenException() {
		Animal goat = new Animal(5, "Goat");
		list.add(goat);

		convertListAfterJava8(list);
	}

	@Test
	public void whenMapHasDuplicateKeyThenMergeFunctionHandlesCollision() {
		Animal goat = new Animal(5, "Goat2");
		list.add(goat);

		Map<Integer, Animal> map = listToMapWithDupKey(list);
		assertEquals(5, map.size());
		assertEquals("Goat", map.get(5).getName());
	}

	// Arrays.asList返回的ArrayList无法add
	@Test(expected = UnsupportedOperationException.class)
	public void givenAnArray_whenConvertingToList_returnUnmodifiableListUnitTest() {
		String[] stringArray = new String[] { "A", "B", "C", "D" };
		List<String> stringList = Arrays.asList(stringArray);
		stringList.set(0, "E");
		assertThat(stringList, CoreMatchers.hasItems("E", "B", "C", "D"));
		assertArrayEquals(stringArray, new String[] { "E", "B", "C", "D" });
		stringList.add("F");
	}

	@Test
	public void givenAnArray_whenConvertingToList_returnModifiableListUnitTest() {
		String[] stringArray = new String[] { "A", "B", "C", "D" };
		List<String> stringList = new ArrayList<>(Arrays.asList(stringArray));
		stringList.set(0, "E");
		assertThat(stringList, CoreMatchers.hasItems("E", "B", "C", "D"));
		assertArrayEquals(stringArray, new String[] { "A", "B", "C", "D" });
		stringList.add("F");
		assertThat(stringList, CoreMatchers.hasItems("E", "B", "C", "D", "F"));
	}

	/**
	 * Convert a string list to a map whose key is the string's length and value is the collection with same length.
	 * Give a list {"Baeldung", "is", "very", "cool"}.
	 * After conversion we'll get a map like:
	 * {8 : ["Baeldung"], 2 : ["is"], 4 : ["very", "cool"]}.
	 */
	@Test
	public void givenAList_whenConvertWithJava8GroupBy_thenReturnMap() {
		ListToMapConverter converter = new ListToMapConverter();
		List<String> source = Arrays.asList("List", "Map", "Set", "Tree");
		Map<Integer, List<String>> convertedMap = source.stream()
				.collect(Collectors.groupingBy(String::length,
						HashMap::new,
						Collectors.toCollection(ArrayList::new)));

		assertTrue(convertedMap.get(3).contains("Map"));
	}

	// 同上
	@Test
	public void givenAList_whenConvertWithJava8Collect_thenReturnMap() {
		ListToMapConverter converter = new ListToMapConverter();
		List<String> source = Arrays.asList("List", "Map", "Set", "Tree");

		Supplier<List<String>> listSupplier = ArrayList::new;
		Supplier<Map<Integer, List<String>>> mapSupplier = HashMap::new;

		BiConsumer<Map<Integer, List<String>>, String> accumulator = (response, element) -> {
			Integer key = element.length();
			List<String> values = response.getOrDefault(key, listSupplier.get());
			values.add(element);
			response.put(key, values);
		};

		BiConsumer<Map<Integer, List<String>>, Map<Integer, List<String>>> combiner = (res1, res2) -> {
			res1.putAll(res2);
		};

		Map<Integer, List<String>> convertedMap =  source.stream()
				.collect(mapSupplier, accumulator, combiner);

		assertTrue(convertedMap.get(3).contains("Map"));
	}

	// 同上
	@Test
	public void givenAList_whenConvertWithCollectorToMap_thenReturnMap() {
		ListToMapConverter converter = new ListToMapConverter();
		List<String> source = Arrays.asList("List", "Map", "Set", "Tree");

		Function<String, Integer> keyMapper = String::length;
		Supplier<List<String>> listSupplier = ArrayList::new;
		Supplier<Map<Integer, List<String>>> mapSupplier = HashMap::new;

		Function<String, List<String>> valueMapper = (element) -> {
			List<String> collection = listSupplier.get();
			collection.add(element);
			return collection;
		};

		BinaryOperator<List<String>> mergeFunction = (existing, replacement) -> {
			existing.addAll(replacement);
			return existing;
		};

		Map<Integer, List<String>> convertedMap =  source.stream()
				.collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapSupplier));

		assertTrue(convertedMap.get(3).contains("Map"));
	}


	public Map<Integer, Animal> convertListAfterJava8(List<Animal> list) {
		Map<Integer, Animal> map = list.stream().collect(Collectors.toMap(Animal::getId, Function.identity()));
		return map;
	}

	public Map<Integer, Animal> listToMapWithDupKey(List<Animal> books) {
		return books.stream().collect(Collectors.toMap(Animal::getId, Function.identity(), (existing, replacement) -> existing));
	}

	public class Animal {
		private int id;
		private String name;

		public Animal(int id, String name) {
			this.id = id;
			this.setName(name);
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}


	public class ListToMapConverter {

		public Map<Integer, List<String>> groupingByStringLength(List<String> source,
		                                                         Supplier<Map<Integer, List<String>>> mapSupplier,
		                                                         Supplier<List<String>> listSupplier) {

			return source.stream()
					.collect(Collectors.groupingBy(String::length, mapSupplier, Collectors.toCollection(listSupplier)));
		}

		public Map<Integer, List<String>> streamCollectByStringLength(List<String> source,
		                                                              Supplier<Map<Integer, List<String>>> mapSupplier,
		                                                              Supplier<List<String>> listSupplier) {

			BiConsumer<Map<Integer, List<String>>, String> accumulator = (response, element) -> {
				Integer key = element.length();
				List<String> values = response.getOrDefault(key, listSupplier.get());
				values.add(element);
				response.put(key, values);
			};

			BiConsumer<Map<Integer, List<String>>, Map<Integer, List<String>>> combiner = (res1, res2) -> {
				res1.putAll(res2);
			};

			return source.stream()
					.collect(mapSupplier, accumulator, combiner);
		}

		public Map<Integer, List<String>> collectorToMapByStringLength(List<String> source,
		                                                               Supplier<Map<Integer, List<String>>> mapSupplier,
		                                                               Supplier<List<String>> listSupplier) {

			Function<String, Integer> keyMapper = String::length;

			Function<String, List<String>> valueMapper = (element) -> {
				List<String> collection = listSupplier.get();
				collection.add(element);
				return collection;
			};

			BinaryOperator<List<String>> mergeFunction = (existing, replacement) -> {
				existing.addAll(replacement);
				return existing;
			};

			return source.stream()
					.collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapSupplier));
		}

	}

}
