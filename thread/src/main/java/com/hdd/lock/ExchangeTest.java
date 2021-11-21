package com.hdd.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;

/**
 * @author hdd
 * @date 2021-08-31
 * Exchanger 用于两个线程之间交换数据
 */
@Slf4j
public class ExchangeTest {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(() -> {
            try {
                String s = exchanger.exchange("t1");
                log.debug(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(() -> {
            try {
                String s = exchanger.exchange("t2");
                log.debug(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();
    }

}
