package lang.java8.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class Demo {
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

}
