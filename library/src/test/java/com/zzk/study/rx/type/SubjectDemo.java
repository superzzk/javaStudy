package com.zzk.study.rx.type;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import org.junit.Test;

public class SubjectDemo {

	@Test
	public void test_ReplaySubject() {
		ReplaySubject<String> subject = ReplaySubject.create();
		subject.onNext("a");
		subject.onNext("b");
		subject.onNext("c");

		subject.subscribe(System.out::println);

	}

	@Test
	public void test_PublishSubject() {
		PublishSubject<String> subject = PublishSubject.create();
		subject.onNext("a");
		subject.onNext("b");
		subject.onNext("c");

		subject.subscribe(System.out::println);
		subject.onNext("d");
	}
}
