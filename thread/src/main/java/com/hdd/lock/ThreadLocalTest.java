package com.hdd.lock;

/**
 * @author hdd
 * @date 2021-08-20
 */
public class ThreadLocalTest {

    public static void main(String[] args) {
        ThreadLocal<Integer> tl = new ThreadLocal<>();
        tl.set(0);
        Thread thread1 = new Thread(()->{
            System.out.println(Thread.currentThread().getName() + tl.get());
            tl.set(1);
            for (int i=0;i<10;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + tl.get());
            }
        },"thread1");
        thread1.start();
        tl.set(2);
    }

}
