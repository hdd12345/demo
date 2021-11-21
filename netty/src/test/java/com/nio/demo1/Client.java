package com.nio.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author hdd
 * @date 2021-07-14
 */
public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",1234));

        ByteBuffer writeBuff = ByteBuffer.allocate(1024);
        ByteBuffer readBuff = ByteBuffer.allocate(1024);

        writeBuff.put("hey guys".getBytes());
        writeBuff.flip();

        while (true){
            writeBuff.rewind();
            socketChannel.write(writeBuff);
            readBuff.clear();
            socketChannel.read(readBuff);
            System.out.println("server:" + new String(readBuff.array()));
        }
    }

}
