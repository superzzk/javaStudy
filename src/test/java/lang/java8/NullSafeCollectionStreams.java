package lang.java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.junit.Assert.assertEquals;

public class NullSafeCollectionStreams {


	@Test
	public void whenCollectionIsNull_thenExpectAnEmptyStream() {
		final NullSafeCollectionStreamsUsingCommonsEmptyIfNull instance =
				new NullSafeCollectionStreamsUsingCommonsEmptyIfNull();
		Collection<String> collection = null;
		Stream<String> expResult = Stream.empty();
		Stream<String> result = instance.collectionAsStream(collection);
		assertStreamEquals(expResult, result);

	}

	@Test
	public void whenCollectionHasElements_thenExpectAStreamOfExactlyTheSameElements() {
		final NullSafeCollectionStreamsUsingCommonsEmptyIfNull instance =
				new NullSafeCollectionStreamsUsingCommonsEmptyIfNull();
		Collection<String> collection = Arrays.asList("a", "b", "c");
		Stream<String> expResult = Arrays.stream(new String[] { "a", "b", "c" });
		Stream<String> result = instance.collectionAsStream(collection);
		assertStreamEquals(expResult, result);
	}

	private static void assertStreamEquals(Stream<?> s1, Stream<?> s2) {
		Iterator<?> iter1 = s1.iterator(), iter2 = s2.iterator();
		while (iter1.hasNext() && iter2.hasNext())
			assertEquals(iter1.next(), iter2.next());
		assert !iter1.hasNext() && !iter2.hasNext();
	}


	@Test
	public void whenCollectionIsNull_thenExpectAnEmptyStream_useOptional() {
		final NullSafeCollectionStreamsUsingJava8OptionalContainer instance = new NullSafeCollectionStreamsUsingJava8OptionalContainer();
		Collection<String> collection = null;
		Stream<String> expResult = Stream.empty();
		Stream<String> result = instance.collectionAsStream(collection);
		assertStreamEquals(expResult, result);

	}

	@Test
	public void whenCollectionHasElements_thenExpectAStreamOfExactlyTheSameElements_useOptional() {
		final NullSafeCollectionStreamsUsingJava8OptionalContainer instance = new NullSafeCollectionStreamsUsingJava8OptionalContainer();
		Collection<String> collection = Arrays.asList("a", "b", "c");
		Stream<String> expResult = Arrays.stream(new String[] { "a", "b", "c" });
		Stream<String> result = instance.collectionAsStream(collection);
		assertStreamEquals(expResult, result);
	}




	public class NullSafeCollectionStreamsUsingCommonsEmptyIfNull {

		/**
		 * This method shows how to make a null safe stream from a collection through the use of
		 * emptyIfNull() method from Apache Commons CollectionUtils library
		 *
		 * @param collection The collection that is to be converted into a stream
		 * @return The stream that has been created from the collection or an empty stream if the collection is null
		 */
		public Stream<String> collectionAsStream(Collection<String> collection) {
			return emptyIfNull(collection).stream();
		}
	}

	public class NullSafeCollectionStreamsUsingJava8OptionalContainer {

		/**
		 * This method shows how to make a null safe stream from a collection  through the use of
		 * Java SE 8’s Optional Container
		 *
		 * @param collection The collection that is to be converted into a stream
		 * @return The stream that has been created from the collection or an empty stream if the collection is null
		 */
		public Stream<String> collectionAsStream(Collection<String> collection) {
			return Optional.ofNullable(collection)
					.map(Collection::stream)
					.orElseGet(Stream::empty);
		}
	}

}
