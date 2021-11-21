package com.hdd.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author hdd
 * @date 2021-08-31
 * ReadWriteLock 读写锁，多个线程同时读，一个线程写
 */
@Slf4j
public class ReadWriteLockTest {

    static int count;

    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();
        for (int i=0;i<1000;i++){
            new Thread(() -> {
                readLock.lock();
                log.debug("write lock :{}",count);
                readLock.unlock();
            }).start();
        }
        for (int i=0;i<1000;i++){
            new Thread(() -> {
                writeLock.lock();
                count++;
                log.debug("{}",count);
                writeLock.unlock();
            }).start();
        }

    }

}
