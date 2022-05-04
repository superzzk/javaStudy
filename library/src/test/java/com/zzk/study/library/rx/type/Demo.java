package com.zzk.study.rx.type;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.junit.Test;

public class Demo {
	@Test
	public void test_observer_observable1(){
		Observable<String> myObservable = Observable.create(
				sub -> {
					sub.onNext("Hello, world!");
					sub.onComplete();
				}
		);
		Observer<String> mySubscriber = new Observer<String>() {

			@Override
			public void onSubscribe(Disposable d) {
				System.out.println("on subscribe");
			}

			@Override
			public void onNext(String s) {
				System.out.println("Received value:"+ s);
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println("Sequence faulted with:"+ throwable.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("Sequence terminated");
			}
		};

		myObservable.subscribe(mySubscriber);
	}


	@Test
	public void test_observer_observable2(){
		MyObserver myObserver = new MyObserver();
		MySequenceOfNumbers mySequenceOfNumbers = new MySequenceOfNumbers();
		mySequenceOfNumbers.subscribe(myObserver);
	}

	public void test_subject(){

	}

	class MyObserver implements Observer<Integer>{

		@Override
		public void onSubscribe(@NonNull Disposable d) {
			System.out.println("on subscribe");
		}

		@Override
		public void onNext(@NonNull Integer integer) {
			System.out.println("Received value:"+integer);
		}

		@Override
		public void onError(@NonNull Throwable e) {
			System.out.println("Sequence faulted with:"+e.getMessage());
		}

		@Override
		public void onComplete() {
			System.out.println("Sequence terminated");
		}
	}
	public class MySequenceOfNumbers extends Observable<Integer>{

		@Override
		protected void subscribeActual(@NonNull Observer<? super Integer> observer) {
			observer.onNext(1);
			observer.onNext(2);
			observer.onNext(3);
			observer.onComplete();
		}
	}

}
