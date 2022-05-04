package com.zzk.study.library.guava.cache;

import com.google.common.base.Suppliers;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 *
 * 1. Memoization vs. Caching
 * Memoization is similar to caching with regards to memory storage.
 * Both techniques attempt to increase efficiency by reducing the number of calls to computationally expensive code.
 *
 * However, whereas caching is a more generic term that addresses the problem at the level of class instantiation,
 * object retrieval, or content retrieval, memoization solves the problem at the level of method/function execution.
 *
 * 2. Guava Memoizer and Guava Cache
 * Guava supports both memoization and caching.
 * Memoization applies to functions with no argument (Supplier) and functions with exactly one argument (Function).
 * Supplier and Function here refer to Guava functional interfaces which are direct subclasses of Java 8 Functional API interfaces of the same names.
 *
 * We can call memoization APIs on-demand and specify an eviction policy which controls the number of entries held in memory
 * and prevents the uncontrolled growth of memory in use by evicting/removing an entry from the cache once it matches the condition of the policy.
 */
public class GuavaMemoizerUnitTest {

    /*
     * Supplier Memoization
     * There are two methods in the Suppliers class that enable memoization: memoize, and memoizeWithExpiration.
     * When we want to execute the memoized method, we can simply call the get method of the returned Supplier.
     * Depending on whether the method's return value exists in memory,
     * the get method will either return the in-memory value or execute the memoized method and pass the return value to the caller.
     */
    @Test
    public void givenMemoizedSupplier_whenGet_thenSubsequentGetsAreFast() {
        // given
        Supplier<BigInteger> memoizedSupplier;
        memoizedSupplier = Suppliers.memoize(CostlySupplier::generateBigNumber);

        // when
        BigInteger expectedValue = new BigInteger("12345");
        assertSupplierGetExecutionResultAndDuration(memoizedSupplier, expectedValue, 2000D);

        // then
        assertSupplierGetExecutionResultAndDuration(memoizedSupplier, expectedValue, 0D);
        assertSupplierGetExecutionResultAndDuration(memoizedSupplier, expectedValue, 0D);
    }

    /*
     * Suppose we only want to keep the returned value from the Supplier in the memo for a certain period.
     * We can use the Suppliers‘ memoizeWithExpiration method and specify the expiration time with its
     * corresponding time unit (e.g., second, minute), in addition to the delegated Supplier:
     */
    @Test
    public void givenMemoizedSupplierWithExpiration_whenGet_thenSubsequentGetsBeforeExpiredAreFast() throws InterruptedException {
        // given
        Supplier<BigInteger> memoizedSupplier;
        memoizedSupplier = Suppliers.memoizeWithExpiration(CostlySupplier::generateBigNumber, 3, TimeUnit.SECONDS);

        // when
        BigInteger expectedValue = new BigInteger("12345");
        assertSupplierGetExecutionResultAndDuration(memoizedSupplier, expectedValue, 2000D);

        // then
        assertSupplierGetExecutionResultAndDuration(memoizedSupplier, expectedValue, 0D);
        // add one more second until memoized Supplier is evicted from memory
        TimeUnit.SECONDS.sleep(3);
        assertSupplierGetExecutionResultAndDuration(memoizedSupplier, expectedValue, 2000D);
    }

    /*
     * Function Memoization
     * To memoize a method that takes a single argument we build a LoadingCache map using CacheLoader‘s from
     * method to provision the builder concerning our method as a Guava Function.
     * LoadingCache is a concurrent map, with values automatically loaded by CacheLoader.
     * CacheLoader populates the map by computing the Function specified in the from method, and putting the returned value into the LoadingCache.
     */
    @Test
    public void givenInteger_whenGetFibonacciNumber_thenShouldCalculateFibonacciNumber() {
        // given
        int n = 95;

        // when
        BigInteger fibonacciNumber = FibonacciSequence.getFibonacciNumber(n);

        // then
        BigInteger expectedFibonacciNumber = new BigInteger("31940434634990099905");
        assertThat(fibonacciNumber, is(equalTo(expectedFibonacciNumber)));
    }

    @Test
    public void givenInteger_whenGetFactorial_thenShouldCalculateFactorial() {
        // given
        int n = 95;

        // when
        BigInteger factorial = Factorial.getFactorial(n);

        // then
        BigInteger expectedFactorial = new BigInteger("10329978488239059262599702099394727095397746340117372869212250571234293987594703124871765375385424468563282236864226607350415360000000000000000000000");
        assertThat(factorial, is(equalTo(expectedFactorial)));
    }



    private <T> void assertSupplierGetExecutionResultAndDuration(Supplier<T> supplier,
                                                                 T expectedValue,
                                                                 double expectedDurationInMs) {
        Instant start = Instant.now();
        T value = supplier.get();
        Long durationInMs = Duration.between(start, Instant.now()).toMillis();
        double marginOfErrorInMs = 100D;

        assertThat(value, is(equalTo(expectedValue)));
        assertThat(durationInMs.doubleValue(), is(closeTo(expectedDurationInMs, marginOfErrorInMs)));
    }

    public static class CostlySupplier {
        public static BigInteger generateBigNumber() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
            return new BigInteger("12345");
        }
    }

    public static class Factorial {

        private static LoadingCache<Integer, BigInteger> memo = CacheBuilder.newBuilder()
                .build(CacheLoader.from(Factorial::getFactorial));

        public static BigInteger getFactorial(int n) {
            if (n == 0) {
                return BigInteger.ONE;
            } else {
                return BigInteger.valueOf(n).multiply(memo.getUnchecked(n - 1));
            }
        }
    }

    public static class FibonacciSequence {

        private static LoadingCache<Integer, BigInteger> memo = CacheBuilder.newBuilder()
                .maximumSize(100)
                .build(CacheLoader.from(FibonacciSequence::getFibonacciNumber));

        public static BigInteger getFibonacciNumber(int n) {
            if (n == 0) {
                return BigInteger.ZERO;
            } else if (n == 1) {
                return BigInteger.ONE;
            } else {
                return memo.getUnchecked(n - 1).add(memo.getUnchecked(n - 2));
            }
        }
    }

}