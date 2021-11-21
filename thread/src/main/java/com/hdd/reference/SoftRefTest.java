package com.hdd.reference;

import java.lang.ref.SoftReference;

/**
 * @author hdd
 * @date 2021-08-20
 * SoftReference 软引用，当堆内存不够时会被gc
 */
public class SoftRefTest {

    public static void main(String[] args) {
        SoftReference<byte[]> softReference = new SoftReference<>(new byte[1024*1024*15]);
        System.out.println(softReference.get());
        System.gc();
        System.out.println(softReference.get());
        byte[] ba = new byte[1024*1024*15];
        System.out.println(softReference.get());
    }

}
