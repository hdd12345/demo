package com.hdd.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author hdd
 * @date 2021-09-01
 * park() 阻塞线程  unpark()唤醒线程
 */
public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if(i == 5){
                    LockSupport.park();
                }
                System.out.println(i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        TimeUnit.SECONDS.sleep(8);
        LockSupport.unpark(thread);

    }

}
