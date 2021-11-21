package com.hdd.jvm;

/**
 * @author hdd
 * @date 2021-09-07
 */
public class StackOverflowTest {

    static void test(int i){
        try{
            test(++i);
        }catch(Throwable e){
            System.out.println(e);
            System.out.println("栈深度:"+i);
        }

    }

    static void a(){

    }

    public static void main(String[] args) {
//        test(0);
        //IntStream.range(1,7).forEach(System.out::println);
    }

}
