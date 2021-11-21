package com.hdd.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

/**
 * @author hdd
 * @date 2021-08-31
 * 写两个线程，线程1添加10个元素到容器，线程2对元素个数做监控，个数为5时，线程2给出提示并结束
 */
public class Test {

    static List<Integer> list = new ArrayList<>();

    static void add(int e){
        list.add(e);
    }

    static int size(){
        return list.size();
    }

    public static void main(String[] args) {
//        run1();
//        run2();
//        run3();
        run4();
    }

    static void run1(){
        Object obj = new Object();
        new Thread(() -> {
            synchronized (obj){
                System.out.println("t2 start");
                while (true){
                    if(size() != 5){
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        obj.notify();
                        break;
                    }
                }
                System.out.println("t2 end");
            }
        }, "t2").start();
        new Thread(() -> {
            synchronized (obj){
                for (int i=0;i<10;i++){
                    list.add(i);
                    System.out.println("t1 add "+i);
                    if(size() == 5){
                        try {
                            obj.notify();
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "t1").start();
    }

    static void run2(){
        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(1);
        new Thread(() -> {
            System.out.println("t2 start");
            while (true){
                if(size() != 5){
                    try {
                        countDownLatch1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    break;
                }
            }
            System.out.println("t2 end");
            countDownLatch2.countDown();
        }, "t2").start();
        new Thread(() -> {
            for (int i=0;i<10;i++){
                list.add(i);
                System.out.println("t1 add "+i);
                if(size() == 5){
                    countDownLatch1.countDown();
                    try {
                        countDownLatch2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();
    }

    static Thread t1,t2;

    static void run3(){
        t2 = new Thread(() -> {
            System.out.println("t2 start");
            while (true){
                if(size() != 5){
                    LockSupport.park();
                }else{
                    break;
                }
            }
            System.out.println("t2 end");
            LockSupport.unpark(t1);
        }, "t2");
        t1 = new Thread(() -> {
            for (int i=0;i<10;i++){
                list.add(i);
                System.out.println("t1 add "+i);
                if(size() == 5){
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
        }, "t1");
        t2.start();
        t1.start();
    }

    static void run4(){
        Semaphore semaphore = new Semaphore(1,true);
        new Thread(() -> {
            System.out.println("t2 start");
            while (true){
                if(size() != 5){
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    break;
                }
            }
            System.out.println("t2 end");
            semaphore.release();
        }, "t2").start();
        new Thread(() -> {
            for (int i=0;i<10;i++){
                list.add(i);
                System.out.println("t1 add "+i);
                if(size() == 5){
                    semaphore.release();
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();
    }

}
