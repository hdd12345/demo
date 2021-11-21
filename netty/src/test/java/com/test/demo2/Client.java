package com.test.demo2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author hdd
 * @date 2021-07-22
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect("127.0.0.1",1234);
        channelFuture.sync();
        Channel channel = channelFuture.channel();
        Scanner scanner = new Scanner(System.in);
        while (true){
            String msg = scanner.nextLine();
            if("quit".equals(msg))
                break;
            channel.writeAndFlush(msg);
        }
    }

}
