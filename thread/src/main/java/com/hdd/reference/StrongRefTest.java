package com.hdd.reference;

/**
 * @author hdd
 * @date 2021-08-20
 */
public class StrongRefTest {

    public static void main(String[] args) {
        User user = new User();
        System.out.println(user);
        System.gc();
        System.out.println(user);
    }

}
