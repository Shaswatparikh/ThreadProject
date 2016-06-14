package main.java.demo2;

/**
 * Created by user on 9/6/16.
 */

class Runner implements Runnable {
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

}

public class TestApp{
    public static void main(String[] st){
        Thread thread1 = new Thread(new Runner());
        Thread thread2 = new Thread(new Runner());

        thread1.start();
        thread2.start();
    }
}
