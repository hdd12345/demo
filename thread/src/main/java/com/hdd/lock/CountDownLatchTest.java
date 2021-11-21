package com.hdd.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author hdd
 * @date 2021-08-31
 * CountDownLatch await()阻塞线程，当count减为0时线程继续运行
 */
@Slf4j
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            try {
                log.debug("thread start!");
                countDownLatch.await();
                log.debug("thread end!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(3);
        countDownLatch.countDown();
        countDownLatch.countDown();
    }

}
