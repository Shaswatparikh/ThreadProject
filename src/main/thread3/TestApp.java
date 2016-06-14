package main.thread3;

import sun.org.mozilla.javascript.internal.Synchronizer;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by user on 10/6/16.
 */

class Processor{

    void producer() throws InterruptedException {
        synchronized (this){
            System.out.println("Producer thread running...");
            //Only called in synchronized context
            wait();
            //wait(1000);
            System.out.println("Resumed...");
        }
    }

    void consumer() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);
        synchronized (this){
            System.out.println("Consumer thread running...");
            System.out.println("Waiting for return key...");
            String next = scanner.nextLine();
            System.out.println("Return key pressed...");
            //Only called in synchronized context
            notify();
            //notifyAll();
        }
    }
}

public class TestApp {

    public static void main(String[] st) {

        final Processor processor = new Processor();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
