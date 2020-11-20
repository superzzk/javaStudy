package lang.java8.stream;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

	@Test
	public void givenIterable_whenConvertedToStream_thenNotNull() {
		Iterable<String> iterable = Arrays.asList("Testing", "Iterable", "conversion", "to", "Stream");

		Assert.assertNotNull(StreamSupport.stream(iterable.spliterator(), false));
	}

	@Test
	public void whenConvertedToList_thenCorrect() {
		Iterable<String> iterable = Arrays.asList("Testing", "Iterable", "conversion", "to", "Stream");

		List<String> result = StreamSupport.stream(iterable.spliterator(), false).map(String::toUpperCase).collect(Collectors.toList());

		assertThat(result, contains("TESTING", "ITERABLE", "CONVERSION", "TO", "STREAM"));
	}

	@Test(expected = IllegalStateException.class)
	public void givenStream_whenStreamUsedTwice_thenThrowException() {
		Stream<String> stringStream = Stream.of("A", "B", "C", "D");
		Optional<String> result1 = stringStream.findAny();
		System.out.println(result1.get());
		Optional<String> result2 = stringStream.findFirst();
		System.out.println(result2.get());
	}

	@Test
	public void givenStream_whenUsingSupplier_thenNoExceptionIsThrown() {
		try {
			Supplier<Stream<String>> streamSupplier = () -> Stream.of("A", "B", "C", "D");
			Optional<String> result1 = streamSupplier.get().findAny();
			System.out.println(result1.get());
			Optional<String> result2 = streamSupplier.get().findFirst();
			System.out.println(result2.get());
		} catch (IllegalStateException e) {
			fail();
		}
	}

}
