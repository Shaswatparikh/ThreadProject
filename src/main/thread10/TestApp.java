package main.thread10;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by user on 10/6/16.
 */

public class TestApp {

    public static void main(String[] st) throws InterruptedException {
        System.out.println("Starting...");
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?> future = executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws IOException {
                Random random = new Random();

                for (int i = 0; i < 1E8; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("We have been interrupted!");
                        break;
                    }
                    Math.sin(random.nextDouble());
                }
                return null;
            }
        });

        executorService.shutdown();

        Thread.sleep(500);

        /**
         * Attempts to cancel execution of this task.  This attempt will
         * fail if the task has already completed, has already been cancelled,
         * or could not be cancelled for some other reason. If successful,
         * and this task has not started when <tt>cancel</tt> is called,
         * this task should never run.  If the task has already started,
         * then the <tt>mayInterruptIfRunning(true/false)</tt> parameter determines
         * whether the thread executing this task should be interrupted in
         * an attempt to stop the task.*/

        //future.cancel(true);
        executorService.shutdownNow();

        executorService.awaitTermination(1, TimeUnit.DAYS);

        System.out.println("Finishing...");
    }
}