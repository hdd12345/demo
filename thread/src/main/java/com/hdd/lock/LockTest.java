package com.hdd.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hdd
 * @date 2021-08-20
 */
public class LockTest {

    static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        interruptTest();
    }

    static void run1(){
        Lock lock = new ReentrantLock();
        for (int i=0;i<1000;i++){
            new Thread(() -> {
                while (!lock.tryLock()){

                }
                count++;
                System.out.println(count);
                /*try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                lock.unlock();
            }).start();
        }
    }

    static void interruptTest() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Thread t = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                Thread.sleep(3000);
                count++;
                System.out.println(count);
                lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("interrupt");
            }
        });
        t.start();
        TimeUnit.SECONDS.sleep(1);
        t.interrupt();
    }

}
