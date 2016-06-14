package main.java.demo5;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by user on 9/6/16.
 */

public class TestApp{

    private volatile int count = 0;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] st){

        TestApp testApp = new TestApp();
        testApp.doWork();
    }

    public void doWork(){
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<100; i++){
                    increment();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<100; i++){
                    increment();
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("Count is: "+ count);
        System.out.print("AtomicInteger is: "+ atomicInteger.toString());
    }

    /*
     * It will acquire Intrinsic lock OR mutex on Count & AtomicInteger.
     * For maintaining data integrity we have acquire lock on so that multiple
     * thread simultaneously not access and make data stale
     */
    private synchronized void increment() {
        count++;
        atomicInteger.incrementAndGet();
    }
}
