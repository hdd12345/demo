package com.test.demo1;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

/**
 * @author hdd
 * @date 2021-07-21
 */
public class EventLoopTest {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        EventLoop eventLoop = group.next();

        eventLoop.submit(() -> {
            System.out.println("普通任务");
        });

        eventLoop.schedule(() -> {
            System.out.println("延时任务");
        }, 1, TimeUnit.SECONDS);

        /**
         * 上一个任务开始时计算间隔时间,若间隔时间达到时上一个任务还未结束，则等上一次任务结束再执行
         */
        /*eventLoop.scheduleAtFixedRate(() -> {
            System.out.println("定时任务");
        }, 5,1, TimeUnit.SECONDS);*/

        //从结束时间开始等待delay
        eventLoop.scheduleWithFixedDelay(() -> {
            System.out.println("任务");
        }, 5,1, TimeUnit.SECONDS);
    }

}
