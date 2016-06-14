package main.thread7;

import java.util.concurrent.Semaphore;

/**
 * Created by user on 14/6/16.
 */
public class Connection {

    private static Connection instance = new Connection();
    private int connections = 0;
    /*
     * We can pass fairness parameter in Semaphore.
     * It make sure that the threads waiting for getting lock when we call acquire()
     * when permits are zero.
     * If fairness is true than they will get sequential access. while false will randomly allocate
     */
    private Semaphore semaphore = new Semaphore(10);
    private Connection(){

    }

    public static Connection getInstance(){
        return instance;
    }

    /* If code between acquire() and release() throws exception than release never get call
    Thread programming most important aspect is to avoid infinity lock
    public void connect() throws InterruptedException {
        semaphore.acquire();
        synchronized (this){
            connections++;
            System.out.println("Current Connections: " + connections);
        }

        Thread.sleep(2000);

        synchronized (this){
            connections--;
        }
        semaphore.release();
    }*/

    public void connect() throws InterruptedException {

        semaphore.acquire();
        try {
            doConnect();
        } finally {
            semaphore.release();
        }
    }

    public void doConnect() throws InterruptedException {
        synchronized (this){
            connections++;
            System.out.println("Current Connections: " + connections);
        }

        Thread.sleep(2000);

        synchronized (this){
            connections--;
        }
    }
}
