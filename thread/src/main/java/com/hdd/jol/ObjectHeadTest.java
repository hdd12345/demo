package com.hdd.jol;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author hdd
 * @date 2021-08-11
 */
@Slf4j
public class ObjectHeadTest {

    public static void main(String[] args) throws InterruptedException {
        User user = new User();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
        //new Thread(new MyRunnable(user)).start();
        synchronized (user){
            int i=10;
            while (i>0){
                try {
                    System.out.println(ClassLayout.parseInstance(user).toPrintable());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i--;
            }
        }
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
        /*Thread.sleep(2000);
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
        Thread.sleep(10000);
        System.out.println(ClassLayout.parseInstance(user).toPrintable());*/
        /*new Thread(new MyRunnable(user)).start();
//        Thread.sleep(50);
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
        Thread.sleep(2000);
        System.out.println(ClassLayout.parseInstance(user).toPrintable());*/
    }

}

@Getter
@Setter
class User {
    private Integer id;
}
