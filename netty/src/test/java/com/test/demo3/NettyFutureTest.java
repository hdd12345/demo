package com.test.demo3;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author hdd
 * @date 2021-07-26
 */
@Slf4j
public class NettyFutureTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        EventLoop eventLoop = new NioEventLoopGroup().next();
        Future<Integer> future = eventLoop.submit(() -> {
            log.debug("running...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
//        log.debug("{}",future.get());
//        log.debug("{}",future.getNow());
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.debug("{}",future.get());
            }
        });
    }

}
