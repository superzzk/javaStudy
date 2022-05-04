package com.zzk.study.reactor;

import org.junit.After;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Demo {

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

    @Test
    public void shortCircuit() {
        Flux<String> helloPauseWorld = Mono.just("Hello")
                .concatWith(Mono.just("world").delaySubscription(Duration.ofMillis(500)));
        helloPauseWorld.subscribe(System.out::println);
    }

    @Test
    public void blocks() {
        Flux<String> helloPauseWorld = Mono.just("Hello")
                .concatWith(Mono.just("world").delaySubscription(Duration.ofMillis(500)));
        helloPauseWorld.toStream()
                .forEach(System.out::println);
    }

    /**
     * In the following example, we create a Mono whose start is delayed by 450ms
     * and a Flux that emits its values with a 400ms pause before each value.
     * When firstWithSignal() them together, since the first value from the Flux comes in before the Mono's value,
     * it is the Flux that ends up being played:
     * */
    @Test
    public void test_firstWithSignal() {
        Mono<String> a = Mono.just("oops I'm late").delaySubscription(Duration.ofMillis(450));
        Flux<String> b = Flux.just("let's get", "the party", "started").delayElements(Duration.ofMillis(400));

        Flux.firstWithSignal(a, b)
                .toIterable()
                .forEach(System.out::println);
    }

    @After
    public void after() throws InterruptedException {
        Thread.sleep(3_000);
    }

}
