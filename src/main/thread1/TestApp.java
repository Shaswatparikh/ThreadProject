package main.thread1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 10/6/16.
 */

class Processor implements Runnable{

    private CountDownLatch countDownLatch;

    public Processor(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("Starting Thread...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        countDownLatch.countDown();

        System.out.println("Completing Thread...");
    }
}

public class TestApp {
    public static void main(String[] st){

        CountDownLatch countDownLatch = new CountDownLatch(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for(int i=0 ; i<3; i++){
            executorService.submit(new Processor(countDownLatch));
        }

        try {
            /**
             * Causes the current thread to wait until the latch has counted down to
             * zero, unless the thread is {@linkplain Thread#interrupt interrupted}.
             *
             * <p>If the current count is zero then this method returns immediately.
             *
             * <p>If the current count is greater than zero then the current
             * thread becomes disabled for thread scheduling purposes and lies
             * dormant until one of two things happen:
             * <ul>
             * <li>The count reaches zero due to invocations of the
             * {@link #countDown} method; or
             * <li>Some other thread {@linkplain Thread#interrupt interrupts}
             * the current thread.
             * </ul>
             *
             * <p>If the current thread:
             * <ul>
             * <li>has its interrupted status set on entry to this method; or
             * <li>is {@linkplain Thread#interrupt interrupted} while waiting,
             * </ul>
             * then {@link InterruptedException} is thrown and the current thread's
             * interrupted status is cleared.
             *
             * @throws InterruptedException if the current thread is interrupted
             *         while waiting
             */
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed...");
    }
}
