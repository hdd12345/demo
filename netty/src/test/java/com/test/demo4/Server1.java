package com.test.demo4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hdd
 * @date 2021-07-29
 */
@Slf4j
public class Server1 {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_RCVBUF,10)//接收缓冲区大小
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                log.debug("client connect {}",ctx.channel());
                                super.channelActive(ctx);
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                log.debug("client disconnect {}",ctx.channel());
                                super.channelInactive(ctx);
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(1234);
        log.debug("{} binding...",channelFuture.channel());
        channelFuture.sync();
        log.debug("{} bound...",channelFuture.channel());
        channelFuture.channel().closeFuture().sync();
    }

}
