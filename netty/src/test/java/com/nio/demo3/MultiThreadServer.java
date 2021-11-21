package com.nio.demo3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hdd
 * @date 2021-07-16
 */
public class MultiThreadServer {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress("127.0.0.1",1234));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        Worker[] workers = new Worker[16];
        for (int i=0;i<16;i++){
            workers[i] = new Worker("worker-" + i);
        }
        AtomicInteger index = new AtomicInteger();
        while(true){
            selector.select();
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()){
                SelectionKey selectionKey = it.next();
                it.remove();
                if(selectionKey.isAcceptable()){
                    SocketChannel socketChannel = ssc.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("client connect..." + socketChannel.getRemoteAddress());
                    workers[index.getAndIncrement() & 15].register(socketChannel);
                }
            }
        }
    }

    static class Worker implements Runnable{
        private Thread thread;
        private Selector workerSelector;
        private String name;
        private volatile boolean start = false;
        private ConcurrentLinkedDeque<Runnable> queue = new ConcurrentLinkedDeque<>();

        public Worker(String name){
            this.name = name;
        }

        /**
         * 初始化线程和selector
         */
        public void register(SocketChannel socketChannel) throws IOException {
            if(!start){
                thread = new Thread(this,name);
                workerSelector = Selector.open();
                thread.start();
                start = true;
            }
            queue.add(() -> {
                try {
                    socketChannel.register(workerSelector,SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            workerSelector.wakeup();
        }

        @Override
        public void run() {
            while (true){
                try {
                    workerSelector.select();
                    Runnable task = queue.poll();
                    if(task != null){
                        task.run();//注册
                    }
                    Iterator<SelectionKey> it = workerSelector.selectedKeys().iterator();
                    while (it.hasNext()){
                        SelectionKey key = it.next();
                        it.remove();
                        if(key.isReadable()){
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            System.out.println("before read..."+socketChannel.getRemoteAddress());
                            socketChannel.read(buffer);
                            buffer.flip();
                            System.out.println(Charset.defaultCharset().decode(buffer));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
