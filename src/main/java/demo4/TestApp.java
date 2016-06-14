package main.java.demo4;

import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Created by user on 9/6/16.
 */

class Processor extends Thread{

    private volatile boolean running = true;

    @Override
    public void run(){
        while (running){
            System.out.println("Hello...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutDown(){
            running = false;
    }
}

public class TestApp{
    public static void main(String[] st){
        Processor processor = new Processor();
        processor.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        processor.shutDown();
    }
}
