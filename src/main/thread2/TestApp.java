package main.thread2;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by user on 10/6/16.
 */

public class TestApp {

    // Thread safe Queue with ReentrantLock mechanism.
    private static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(5);

    public static void main(String[] st) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }

    private static void producer() throws InterruptedException {
        Random random = new Random();
        while (true){
            Integer give = random.nextInt(100);
            /**
             * Inserts the specified element into this queue, waiting if necessary
             * for space to become available.
             */
            blockingQueue.put(give);
            System.out.println("Given Value: " + give + " & Queue Size: "+ blockingQueue.size());
        }
    }

    private static void consumer() throws InterruptedException {
        Random random = new Random();
        while (true){
            Thread.sleep(100);
            if(random.nextInt(10) == 0) {
                /**
                 * Retrieves and removes the head of this queue, waiting if necessary
                 * until an element becomes available.
                 */
                Integer take = blockingQueue.take();
                System.out.println("Taken Value: " + take + " & Queue Size: "+ blockingQueue.size());
            }
        }
    }
}
