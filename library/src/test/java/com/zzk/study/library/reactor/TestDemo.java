package com.zzk.study.reactor;

import org.junit.After;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.Assert.assertThat;

public class TestDemo {
	@Test
	public void test() {
		MyReactiveLibrary library = new MyReactiveLibrary();
		library.alphabet5('x').subscribe(System.out::println);
	}

	@Test
	public void testAlphabet5LimitsToZ() {
		MyReactiveLibrary library = new MyReactiveLibrary();
		StepVerifier.create(library.alphabet5('x'))
				.expectNext("x", "y", "z")
				.expectComplete()
				.verify();
	}

	/**
	 * test the delaying method but without actually waiting for the given amount of seconds, by using the withVirtualTime builder
	 * */
	@Test
	public void testWithDelay() {
		MyReactiveLibrary library = new MyReactiveLibrary();
		Duration testDuration =
				StepVerifier.withVirtualTime(() -> library.withDelay("foo", 30))
						.expectSubscription()
						.thenAwait(Duration.ofSeconds(10))
						.expectNoEvent(Duration.ofSeconds(10))
						.thenAwait(Duration.ofSeconds(10))
						.expectNext("foo")
						.expectComplete()
						.verify();
		System.out.println(testDuration.toMillis() + "ms");
	}

	public class MyReactiveLibrary {

		/**
		 * The first method is intended to return the 5 letters of the alphabet following
		 * (and including) the given starting letter.
		 * */
		public Flux<String> alphabet5(char from) {
			return Flux.range((int) from, 5)
					.take(Math.min(5, 'z' - from + 1))      //只 包含z和之前的字符
					.map(i -> "" + (char) i.intValue());
		}

		/**
		 * The second method returns a flux that emits a given value after a given delay, in seconds.
		 * */
		public Mono<String> withDelay(String value, int delaySeconds) {
			return Mono.just(value)
					.delaySubscription(Duration.ofSeconds(delaySeconds));
		}
	}

	@After
	public void after() throws InterruptedException {
		Thread.sleep(3_000);
	}

}
