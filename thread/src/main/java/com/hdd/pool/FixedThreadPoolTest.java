package com.hdd.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author hdd
 * @date 2021-09-02
 * 创建一个可重用固定个数的线程池，以共享的无界队列方式来运行这些线程
 */
@Slf4j
public class FixedThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executors = Executors.newFixedThreadPool(2);
        executors.execute(() -> {
            log.debug("run");
        });
        TimeUnit.SECONDS.sleep(5);
        executors.execute(() -> {
            log.debug("rrr");
        });
    }

}
