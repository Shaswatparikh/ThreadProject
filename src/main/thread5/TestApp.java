package main.thread5;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by user on 10/6/16.
 */

class Runner{

    private int count = 0;
    /*
     * By using ReentrantLock one can acquire recursive locks on object until certain limit
     * One have to use Condition to acquire recursive lock.
     */
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    void increment(){
        for (int i=0; i<10000; i++){
            count++;
        }
    }

    void firstThread() throws InterruptedException {

        lock.lock();
        System.out.println("Waiting...");
        // It can be used after lock() method in synchronized context
        condition.await();
        System.out.println("Woken up...");
        /*
         * Always have practise to use Lock in try-finally block
         * so in case code between lock() & unlock() throws exception
         * it can work seamlessly
         */
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        System.out.println("Waiting for return key...");
        String next = new Scanner(System.in).nextLine();
        System.out.println("Return key pressed...");
        condition.signal();
        try {
            increment();
        } finally {
            // We have released lock here even though we call signal()
            // so that previous thread acquire lock again.
            lock.unlock();
        }
    }

    void finished(){
        System.out.println("Count is " + count);
    }
}

public class TestApp {

    public static void main(String[] st) throws InterruptedException {

        final Runner runner = new Runner();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.secondThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        runner.finished();
    }
}
