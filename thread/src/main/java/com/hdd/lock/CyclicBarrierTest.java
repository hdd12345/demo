package com.hdd.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author hdd
 * @date 2021-08-31
 * await() 阻塞线程，凑齐指定数量继续运行
 */
@Slf4j
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            log.debug("full");
        });
        for (int i=0;i<6;i++){
            new Thread(() -> {
                log.debug("{} start",Thread.currentThread().getId());
                try {
                    cyclicBarrier.await();
                    log.debug("{} end",Thread.currentThread().getId());
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
