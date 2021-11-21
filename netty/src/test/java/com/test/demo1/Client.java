package com.test.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author hdd
 * @date 2021-07-21
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        //启动类
        new Bootstrap()
                //添加事件循环组
                .group(new NioEventLoopGroup())
                //选择客户端SocketChannel实现
                .channel(NioSocketChannel.class)
                //添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    //建立连接后调用
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect("127.0.0.1",1234)
                .sync()
                .channel()
                .writeAndFlush("1234567890");
    }

}
