package zzk.study.java.core.util.concurrent.future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;

import static junit.framework.Assert.assertEquals;

public class FactorialTaskManualTest {

    private ExecutorService executorService;

    @Before
    public void setup(){
        executorService = Executors.newSingleThreadExecutor();
    }

    @Test
    public void whenTaskSubmitted_ThenFutureResultObtained() throws ExecutionException, InterruptedException {
        FactorialTask task = new FactorialTask(5);
        Future<Integer> future= executorService.submit(task);
        assertEquals(120,future.get().intValue());
    }

    @Test(expected = ExecutionException.class)
    public void whenException_ThenCallableThrowsIt() throws ExecutionException, InterruptedException {
        FactorialTask task = new FactorialTask(-5);
        Future<Integer> future= executorService.submit(task);
        Integer result=future.get().intValue();
    }

    @Test
    public void whenException_ThenCallableDoesntThrowsItIfGetIsNotCalled(){
        FactorialTask task = new FactorialTask(-5);
        Future<Integer> future= executorService.submit(task);
        assertEquals(false,future.isDone());
    }

    @After
    public void cleanup(){
        executorService.shutdown();
    }



    public class FactorialTask implements Callable<Integer> {
        int number;

        public FactorialTask(int number) {
            this.number = number;
        }

        public Integer call() throws InvalidParamaterException {
            int fact=1;
            if(number < 0)
                throw new InvalidParamaterException("Number must be positive");

            for(int count=number;count>1;count--){
                fact=fact * count;
            }

            return fact;
        }

        private class InvalidParamaterException extends Exception {
            public InvalidParamaterException(String message) {
                super(message);
            }
        }
    }
}
