package main.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 10/6/16.
 */

class Processor implements Runnable{

    private int id;

    public Processor(int id){
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Starting Thread Id: " + id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completing Thread Id: " + id);
    }
}

public class TestApp {
    public static void main(String[] st){
        ExecutorService  executorService = Executors.newFixedThreadPool(2);

        for(int i=0; i<5; i++){
            executorService.submit(new Processor(i));
        }

        executorService.execute(new Processor(200));

        /**
         * Initiates an orderly shutdown in which previously submitted
         * tasks are executed, but no new tasks will be accepted.
         * Invocation has no additional effect if already shut down.
         *
         * <p>This method does not wait for previously submitted tasks to
         * complete execution.  Use {@link #awaitTermination awaitTermination}
         * to do that.
         */
        executorService.shutdown();
        //executorService.submit(new Processor(100));
        //executorService.execute(new Processor(200));

        System.out.println("All task submitted...");

        try {
            /**
             * Blocks until all tasks have completed execution after a shutdown
             * request, or the timeout occurs, or the current thread is
             * interrupted, whichever happens first.
             *
             * @param timeout the maximum time to wait
             * @param unit the time unit of the timeout argument
             * @return <tt>true</tt> if this executor terminated and
             *         <tt>false</tt> if the timeout elapsed before termination
             * @throws InterruptedException if interrupted while waiting
             */
            boolean flag = executorService.awaitTermination(3, TimeUnit.SECONDS);
            System.out.println("It will execute after 3 seconds & Flag is: " +flag);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All task completed...");
    }
}
