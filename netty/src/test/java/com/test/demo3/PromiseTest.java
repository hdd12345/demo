package com.test.demo3;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author hdd
 * @date 2021-07-28
 */
@Slf4j
public class PromiseTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop eventLoop = new NioEventLoopGroup().next();
        Promise<Integer> promise = new DefaultPromise<>(eventLoop);
        new Thread(() -> {
            try {
                int i = 1 / 0;
                Thread.sleep(2000);
                promise.setSuccess(2);
            } catch (Exception e) {
                promise.setFailure(e);
                e.printStackTrace();
            }
        }).start();
        //log.debug("{}",promise.getNow());
        promise.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                if(future.isSuccess()){
                    log.debug("{}",promise.getNow());
                }else{
                    log.error(future.cause().getMessage());
                }
            }
        });
    }

}
