package com.hdd.jol;

/**
 * @author hdd
 * @date 2021-08-11
 */
public class MyRunnable implements Runnable{

    private Object lock;

    public MyRunnable(Object lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            int i=10;
            while (i>0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i--;
            }
        }
    }
}
