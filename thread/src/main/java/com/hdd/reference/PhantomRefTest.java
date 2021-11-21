package com.hdd.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;

/**
 * @author hdd
 * @date 2021-08-20
 * PhantomReference虚引用， 对象被回收时相关信息会放入一个队列，监听该队列可以得到通知
 */
public class PhantomRefTest {

    private static boolean isRun = true;

    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue<User> queue = new ReferenceQueue<>();
        new Thread(()->{
            while (isRun){
                Reference<? extends User> reference = queue.poll();
                if(reference != null){
                    System.out.println(reference);
                    try {
                        Field referent = Reference.class.getDeclaredField("referent");
                        referent.setAccessible(true);
                        System.out.println(referent.get(reference));
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        User user = new User();
        PhantomReference<User> phantomReference = new PhantomReference<>(user,queue);
        System.out.println(user);
        user = null;
        Thread.currentThread().sleep(3000);
        System.gc();
        Thread.currentThread().sleep(3000);
        isRun = false;
    }

}
