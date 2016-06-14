package main.thread9;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by user on 10/6/16.
 */

public class TestApp {

    public static void main(String[] st) throws InterruptedException {
        System.out.println("Starting...");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();

                for (int i = 0; i < 1E7; i++) {

                    /* As we call interrupt() method on this
                    We can manage it by doing it as mention below
                    to notify thread that it is getting interrupt from out side
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println("We have been interrupted!");
                        break;
                    }*/

                    /*
                     * More sophisticated solution for interrupt()
                     */

                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("We have been interrupted!");
                        break;
                    }

                    Math.sin(random.nextDouble());
                }
            }
        });

        t.start();

        Thread.sleep(500);

        t.interrupt();

        t.join();

        System.out.println("Finishing...");
    }
}