package main.thread7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 10/6/16.
 */

public class TestApp {

    public static void main(String[] st) throws InterruptedException {

        // We can use it as lock if Semaphore do not have any available limit
        // and we call acquire than it will wait until on same or some x thread
        // call release on it.
        Semaphore semaphore = new Semaphore(1);

        semaphore.release();
        System.out.println("Available Limit: " + semaphore.availablePermits());

        semaphore.acquire();
        semaphore.acquire();
        System.out.println("Available Limit: " + semaphore.availablePermits());

        /**
         * Acquires a permit from this semaphore, blocking until one is
         * available, or the thread is {@linkplain Thread#interrupt interrupted}.
         *
         * <p>Acquires a permit, if one is available and returns immediately,
         * reducing the number of available permits by one.
         *
         * <p>If no permit is available then the current thread becomes
         * disabled for thread scheduling purposes and lies dormant until
         * one of two things happens:
         * <ul>
         * <li>Some other thread invokes the {@link #release} method for this
         * semaphore and the current thread is next to be assigned a permit; or
         * <li>Some other thread {@linkplain Thread#interrupt interrupts}
         * the current thread.
         * </ul>
         *
         * <p>If the current thread:
         * <ul>
         * <li>has its interrupted status set on entry to this method; or
         * <li>is {@linkplain Thread#interrupt interrupted} while waiting
         * for a permit,
         * </ul>
         * then {@link InterruptedException} is thrown and the current thread's
         * interrupted status is cleared.
         *
         * @throws InterruptedException if the current thread is interrupted
         */
        semaphore.release();
        semaphore.acquire();

        System.out.println("Available Limit: " + semaphore.availablePermits());

        /*
         * Acutal example start here
         */

        ExecutorService executorService = Executors.newCachedThreadPool();

        long start = System.currentTimeMillis();
        for(int i=0; i<100; i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection.getInstance().connect();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start));
    }
}
