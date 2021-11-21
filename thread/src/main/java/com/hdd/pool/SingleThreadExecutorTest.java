package com.hdd.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hdd
 * @date 2021-09-02
 * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照顺序(FIFO)执行
 */
public class SingleThreadExecutorTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            System.out.println("running...");
        });
    }

}
