package com.hdd.reference;

import java.lang.ref.WeakReference;

/**
 * @author hdd
 * @date 2021-08-20
 * WeakReference 弱引用  一旦经历gc就会被回收
 */
public class WeakRefTest {

    public static void main(String[] args) {
        WeakReference<User> weakReference = new WeakReference<>(new User());
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());
    }

}
