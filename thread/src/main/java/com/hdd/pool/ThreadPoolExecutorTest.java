package com.hdd.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hdd
 * @date 2021-09-02
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        /**
         * corePoolSize 核心线程数
         * maximumPoolSize 最大线程数
         * keepAliveTime 空闲等待时间
         * unit keepAliveTime的时间单位
         * workQueue 阻塞队列，用来存储等待执行的任务
         *      ArrayBlockingQueue 规定大小的BlockingQueue，其构造必须指定大小。其所含的对象是FIFO顺序排序的。
         *          当线程池中线程数量达到corePoolSize后，再有新任务进来，则会将任务放入该队列的队尾，等待被调度。
         *          如果队列已经是满的，则创建一个新线程，如果线程数量已经达到maxPoolSize，则会执行拒绝策略。
         *      PriorityBlockingQueue 具有优先级的无界阻塞队列，优先级通过参数Comparator实现。若其构造时指定大小，生成的BlockingQueue有大小限制，
         *          不指定大小，其大小有Integer.MAX_VALUE来决定。
         *      LinkedBlockingQueue 基于链表的无界阻塞队列（其实最大容量为Integer.MAX），按照FIFO排序。
         *          由于该队列的近似无界性，当线程池中线程数量达到corePoolSize后，再有新任务进来，会一直存入该队列，
         *          而不会去创建新线程直到maxPoolSize，因此使用该工作队列时，参数maxPoolSize其实是不起作用的。
         *      SynchronousQueue 一个不缓存任务的阻塞队列，生产者放入一个任务必须等到消费者取出这个任务。
         *          也就是说新任务进来时，不会缓存，而是直接被调度执行该任务，如果没有可用线程，则创建新线程，如果线程数量达到maxPoolSize，则执行拒绝策略。
         * threadFactory 线程工厂
         * handler 拒绝执行任务时的处理策略
         *      ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
         *      ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
         *      ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
         *      ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                50,
                2,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        System.out.println(executor.getCorePoolSize());
        executor.execute(() -> {
            System.out.println("running...");
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
