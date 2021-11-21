package com.nio.demo2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author hdd
 * @date 2021-07-15
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress("127.0.0.1",1234));
        ByteBuffer buffer = ByteBuffer.allocate(16);
        while (true){
            SocketChannel socketChannel = ssc.accept();
            socketChannel.read(buffer);
            buffer.flip();
            System.out.println(Charset.defaultCharset().decode(buffer));
            buffer.clear();
        }
    }

}
