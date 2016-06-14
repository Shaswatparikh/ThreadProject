package main.thread6;

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

    private Account account1 = new Account();
    private Account account2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void acquireLock(Lock firstLock, Lock secondLock){
        while (true){
            //Acquire Lock
            boolean got1Lock = false;
            boolean got2Lock = false;

            try{
                got1Lock = firstLock.tryLock();
                got2Lock = secondLock.tryLock();
            } finally {
                if(got1Lock && got2Lock)
                    return;

                if(got1Lock)
                    firstLock.unlock();

                if(got2Lock)
                    secondLock.unlock();
            }
            //Locks not acquire
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void firstThread() throws InterruptedException {
        Random random = new Random();

        for (int i=0; i<10000; i++){
            //lock1.lock();
            //lock2.lock();
            acquireLock(lock1, lock2);
            try {
                Account.transfer(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    void secondThread() throws InterruptedException {
        Random random = new Random();

        for (int i=0; i<10000; i++){
            /*
             * Deadlock: Because of sequence of lock
             * firstThread acquire a lock on lock1 and secondThread acquire lock on lock2
             * Now firstThread want to have lock on lock2 while second wants lock on lock1
             * Which causes deadlock.
             * Solution:
             * 1. Always get locks in same order
             * 2. Can have solution like acquirelock()
             */
            //lock2.lock();
            //lock1.lock();
            acquireLock(lock2, lock1);
            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    void finished(){
        System.out.println("Account1 balance: " + account1.getBalance());
        System.out.println("Account2 balance: " + account2.getBalance());
        System.out.println("Total Balance :" + (account1.getBalance() + account2.getBalance()));
    }
}

class Account{
    private int balance = 10000;

    void deposit(int amount){
        balance += amount;
    }

    void withdraw(int amount){
        balance -= amount;
    }

    int getBalance(){
        return balance;
    }

    static void transfer(Account account1, Account account2, int amount){
        account1.withdraw(amount);
        account2.deposit(amount);
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
