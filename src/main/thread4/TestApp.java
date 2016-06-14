package main.thread4;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by user on 10/6/16.
 */

class Processor{

    private LinkedList<Integer> linkedList = new LinkedList<Integer>();
    private final int LIMIT = 10;

    private Object lock = new Object();

    void producer() throws InterruptedException {
        int value = 0;
        while (true){
            synchronized (lock) {
                /*
                 * It advised to have loop around the wait()
                 * to add double check before thread go ahead
                 * and execute next steps.
                 */
                while (linkedList.size() == LIMIT){
                    lock.wait();
                }

                linkedList.add(value++);
                lock.notify();
            }
        }
    }

    void consumer() throws InterruptedException {
        Random random = new Random();
        while (true){
            synchronized (lock) {

                while (linkedList.size() == 0){
                    lock.wait();
                }

                System.out.print("List size: " + linkedList.size());
                int value = linkedList.removeFirst();
                System.out.println("; Value is: " + value);
                lock.notify();
            }
            Thread.sleep(random.nextInt(1000));
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
