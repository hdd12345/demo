package com.test.demo3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author hdd
 * @date 2021-07-26
 */
@Slf4j
public class JDKFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running...");
                Thread.sleep(10000);
                return 100;
            }
        });
        log.debug("main thread blocking...");
        log.debug("{}",future.get());
        log.debug("finish");
    }

}
