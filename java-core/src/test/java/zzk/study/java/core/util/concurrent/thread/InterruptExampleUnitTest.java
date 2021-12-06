package zzk.study.java.core.util.concurrent.thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InterruptExampleUnitTest {

    @Test
    public void whenPropagateException_thenThrowsInterruptedException() {
        assertThrows(InterruptedException.class, () -> {
            Thread.sleep(1000);
            Thread.currentThread().interrupt();
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        });
    }

    @Test
    public void whenRestoreTheState_thenReturnsTrue() {
        InterruptExample thread1 = new InterruptExample();
        thread1.start();
        thread1.interrupt();
        Assertions.assertTrue(thread1.isInterrupted());
    }
    
    @Test
    public void whenThrowCustomException_thenContainsExpectedMessage() {
        Exception exception = assertThrows(CustomInterruptedException.class, () -> {
            Thread.sleep(1000);
            Thread.currentThread().interrupt();
            if (Thread.interrupted()) {
                throw new CustomInterruptedException("This thread was interrupted");
            }
        });
        String expectedMessage = "This thread was interrupted";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
   
    @Test
    public void whenHandleWithCustomException_thenReturnsTrue() throws CustomInterruptedException{
        Assertions.assertTrue(InterruptExample.handleWithCustomException());
    }

    public static class InterruptExample extends Thread {

        public static void propagateException() throws InterruptedException {
            Thread.sleep(1000);
            Thread.currentThread().interrupt();
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        }

        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public static Boolean handleWithCustomException() throws CustomInterruptedException{
            try {
                Thread.sleep(1000);
                Thread.currentThread().interrupt();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CustomInterruptedException("This thread was interrupted...");
            }
            return Thread.currentThread().isInterrupted();
        }
    }
    public static class CustomInterruptedException extends Exception {

        private static final long serialVersionUID = 1L;

        CustomInterruptedException(String message) {
            super(message);
        }
    }


}
