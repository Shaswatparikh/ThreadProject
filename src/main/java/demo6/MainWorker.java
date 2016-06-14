package main.java.demo6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 9/6/16.
 */
public class MainWorker {

    private Random random = new Random();
    private List<Integer> list1 = new ArrayList<Integer>();
    private List<Integer> list2 = new ArrayList<Integer>();

    /*
     * We can use list1 & list2 to put lock but its good practise use seperate object
     * to locking
     * Java might use some optimization for you object which use are processing.
     */
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void stageOne(){
        synchronized(lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }
    }

    public void stageTwo(){
        synchronized(lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }
    }

    public void process(){
        for (int i=0; i<1000; i++){
            stageOne();
            stageTwo();
        }
    }

    public void main(){
        System.out.println("Start Process...");

        long start = System.currentTimeMillis();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
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

        long end = System.currentTimeMillis();

        System.out.println("Time Taken: " + (end - start));
        System.out.println("List1 Size: " + list1.size() + " List2 Size: " + list2.size());
    }
}
