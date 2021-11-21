package com.hdd.lock;

/**
 * @author hdd
 * @date 2021-08-20
 */
public class DCLTest {

    private static volatile DCLTest instance;
    private DCLTest(){
        System.out.println(1111111111);
    }

    public static DCLTest getInstance(){
        if(instance == null){
            synchronized (DCLTest.class){
                if(instance == null){
                    instance = new DCLTest();
                }
                return instance;
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for(int i=0;i<10000;i++){
            new Thread(() -> {
                DCLTest.getInstance();
            }).start();
        }
    }

}
