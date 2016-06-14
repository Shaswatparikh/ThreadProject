package main.java.demo3;

import java.util.concurrent.Callable;

/**
 * Created by user on 9/6/16.
 */

class Caller implements Callable<String>{

    @Override
    public String call() throws Exception {
        for(int i=0; i<10; i++){
            System.out.println("Hello Callable +" + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return String.valueOf(10);
    }
}

public class TestApp{
    public static void main(String[] st){

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<10; i++){
                    System.out.println("Hello Runnable +" + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Caller caller = new Caller();

        thread1.start();
        try {
            caller.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
