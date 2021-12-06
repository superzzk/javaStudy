package zzk.study.java.core.util.concurrent.test;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

/**
 * https://www.baeldung.com/java-testing-multithreaded
 *
 * Here, we've marked the class with an annotation State, which indicates that it holds data that is mutated by multiple threads.
 * Also, we're using an annotation Actor, which marks the methods that hold the actions done by different threads.
 *
 * Finally, we have a method marked with an annotation Arbiter, which essentially only visits the state once all Actors have visited it.
 * We have also used annotation Outcome to define our expectations.
 *
 * Overall, the setup is quite simple and intuitive to follow. We can run this using a test harness, given by the framework,
 * that finds all classes annotated with JCStressTest and executes them in several iterations to obtain all possible interleavings.
 */
@JCStressTest
@Outcome(id = "1", expect = ACCEPTABLE_INTERESTING, desc = "One update lost.")
@Outcome(id = "2", expect = ACCEPTABLE, desc = "Both updates.")
@State
public class MyCounterJCStressManualTest {

    private MyCounter counter;

    @Actor
    public void actor1() {
        counter.increment();
    }

    @Actor
    public void actor2() {
        counter.increment();
    }

    @Arbiter
    public void arbiter(I_Result r) {
        r.r1 = counter.getCount();
    }

}
