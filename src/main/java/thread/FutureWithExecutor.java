package thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class FutureWithExecutor {
    public static void main(String[] args) {
        Future<String> future = Executors.newSingleThreadExecutor()
                .submit(() -> "Hello World");

        try {
            System.out.print(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
