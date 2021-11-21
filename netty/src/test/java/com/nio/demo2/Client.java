package com.nio.demo2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author hdd
 * @date 2021-07-15
 */
public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1",1234));
        sc.write(Charset.defaultCharset().encode("0123456789abcdef"));
        System.in.read();
    }

}
