package com.zzk.study.library.rx.tutorial;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

public class HelloWorld {
	@Test
	public void test1() {
		Flowable.just("Hello world").subscribe(System.out::println);
	}

	@Test
	public void hello() {
		Flowable.fromArray(new String[]{"a", "b"}).subscribe(s -> System.out.println("Hello " + s + "!"));
	}

	@Test
	public void create() {
		Observable.create(emitter -> {
			while (!emitter.isDisposed()) {
				long time = System.currentTimeMillis();
				emitter.onNext(time);
				if (time % 2 != 0) {
					emitter.onError(new IllegalStateException("Odd millisecond!"));
					break;
				}
			}
		})
				.subscribe(System.out::println, Throwable::printStackTrace);
	}

	@Test
	public void background_computation() throws InterruptedException {
		Flowable.fromCallable(() -> {
			Thread.sleep(1000); //  imitate expensive computation
			return "Done";
		})
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.single())
				.subscribe(System.out::println, Throwable::printStackTrace);

		Thread.sleep(2000); // <--- wait for the flow to finish
	}

	@Test
	public void flow(){
		Flowable.range(1, 10)
				.observeOn(Schedulers.computation())
				.map(v -> v * v)
				.blockingSubscribe(System.out::println);

		System.out.println("--------------------------------");

		//parallel
		//The operator flatMap does this by first mapping each number from 1 to 10 into its own individual Flowable,
		//runs them and merges the computed squares.
		Flowable.range(1, 10)
				.flatMap(v ->
						Flowable.just(v)
								.subscribeOn(Schedulers.computation())
								.map(w -> w * w)
				)
				.blockingSubscribe(System.out::println);
		System.out.println("--------------------------------");

		// parallel and sequential
		Flowable.range(1, 10)
				.parallel()
				.runOn(Schedulers.computation())
				.map(v -> v * v)
				.sequential()
				.blockingSubscribe(System.out::println);
		System.out.println("--------------------------------");
	}
}
