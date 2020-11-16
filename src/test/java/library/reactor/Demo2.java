package library.reactor;

import org.junit.After;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Demo2 {
    @Test
    public  void test_flatMap_zipWith() {
        List<String> words = Arrays.asList(
                "one",
                "two",
                "three",
                "hello",
                "world"
        );

        Flux.fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .distinct()
                .sort()
                .zipWith(Flux.range(1, 100), (word, line) -> line + ". " + word)
                .subscribe(System.out::println);
    }

    @Test
    public void test_merge() {
        Flux fastClock = Flux.interval(Duration.ofSeconds(1)).map(tick->"fast" + tick);
        Flux slowClock = Flux.interval(Duration.ofSeconds(2)).map(tick->"slow" + tick);

        Flux clock = Flux.merge(
                fastClock,
                slowClock
        );
        clock.subscribe(System.out::println);
    }

    @After
    public void after() throws InterruptedException {
        Thread.sleep(30_000);
    }

}
