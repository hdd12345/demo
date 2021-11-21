package com.hdd.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author hdd
 * @date 2021-09-02
 * 创建一个定长线程池，支持定时及周期性任务执行
 */
public class ScheduledThreadPoolTest {

    public static void main(String[] args) {
        int a = 65536;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        //延时任务
        executor.schedule(() -> {
            System.out.println("t1 running...");
        },1, TimeUnit.SECONDS);
        //定时周期任务
        executor.scheduleAtFixedRate(() -> {
            System.out.println("t2 running...");
        },1,1, TimeUnit.SECONDS);
    }

}
