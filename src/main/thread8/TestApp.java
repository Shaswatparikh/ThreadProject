package main.thread8;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by user on 10/6/16.
 */

public class TestApp {

    public static void main(String[] st) throws InterruptedException {

        /**
         * Creates a thread pool that creates new threads as needed, but
         * will reuse previously constructed threads when they are
         * available.  These pools will typically improve the performance
         * of programs that execute many short-lived asynchronous tasks.
         * Calls to <tt>execute</tt> will reuse previously constructed
         * threads if available. If no existing thread is available, a new
         * thread will be created and added to the pool. Threads that have
         * not been used for sixty seconds are terminated and removed from
         * the cache. Thus, a pool that remains idle for long enough will
         * not consume any resources. Note that pools with similar
         * properties but different details (for example, timeout parameters)
         * may be created using {@link ThreadPoolExecutor} constructors.
         *
         * @return the newly created thread pool
         */
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int delay = random.nextInt(1000);
                System.out.println("Starting Runnable...");
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Finishing Runnable...");
            }
        });

        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws IOException {
                Random random = new Random();
                int delay = random.nextInt(4000);
                System.out.println("Starting Callable...");
                if(delay > 1000){
                    throw new IOException("Taking too loonggg....");
                }
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Finishing Callable...");
                return delay;
            }
        });

        Future<?> future1 = executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws IOException {
                Random random = new Random();
                int delay = random.nextInt(4000);
                System.out.println("Starting Callable...");
                if(delay > 4000){
                    throw new IOException("Taking too loonggg....");
                }
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Finishing Callable...");
                return null;
            }
        });

        executorService.shutdown();
        System.out.println("Waiting for Future Value....");
        try {
            /**
             * Waits if necessary for the computation to complete, and then
             * retrieves its result.
             */
            System.out.println("Future Value: " + future.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            /**
             * Waits if necessary for the computation to complete, and then
             * retrieves its result.
             */
            System.out.println("Future Value: " + future1.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
