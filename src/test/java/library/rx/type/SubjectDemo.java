package library.rx.type;

import io.reactivex.rxjava3.subjects.ReplaySubject;
import org.junit.Test;

public class ReplaySubjectDemo {

	@Test
	public void test() {
		ReplaySubject<String> subject = ReplaySubject.create();
		subject.onNext("a");
		subject.onNext("b");
		subject.onNext("c");

		subject.subscribe(System.out::println);

	}
}
