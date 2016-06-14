package main.java.demo1;

/**
 * Created by user on 9/6/16.
 */

class Runner extends Thread {
    @Override
    public void run() {
        for (int i=0; i<10; i++){
            System.out.println("Hello Thread +" + i);
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
        Runner runner1 = new Runner();
        runner1.start();

        Runner runner2 = new Runner();
        runner2.start();
    }
}
