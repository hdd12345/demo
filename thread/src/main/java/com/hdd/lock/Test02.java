package com.hdd.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hdd
 * @date 2021-09-01
 * 两个线程分别打印 1 a 2 b 3 c 4 d ...
 */
@Slf4j
public class Test02 {

    public static void main(String[] args) {
        Object obj = new Object();
        new Thread(() -> {
            synchronized (obj){
                for (int i=1;i<=26;i++){
                    log.debug("{}",i);
                    try {
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t1").start();
        new Thread(() -> {
            synchronized (obj){
                for (int i=0;i<26;i++){
                    log.debug("{}",(char)(i+'a'));
                    try {
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t2").start();
    }

}
