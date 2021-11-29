package zzk.study.java.core.util.concurrent.forkjoin;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static org.junit.Assert.assertEquals;

public class RecursiveTaskDemo {

    @Test
    public void whenCalculatesFactorialSquare_thenReturnCorrectValue() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        FactorialSquareCalculator calculator = new FactorialSquareCalculator(10);

        forkJoinPool.execute(calculator);

        Assert.assertEquals("The sum of the squares from 1 to 10 is 385", 385, calculator.join().intValue());
    }

    public class FactorialSquareCalculator extends RecursiveTask<Integer> {
        private static final long serialVersionUID = 1L;

        final private Integer n;

        FactorialSquareCalculator(Integer n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }

            FactorialSquareCalculator calculator = new FactorialSquareCalculator(n - 1);
            calculator.fork();

            return n * n + calculator.join();
        }
    }
}
