package com.test.demo4;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author hdd
 * @date 2021-07-29
 */
@Slf4j
public class Client4 {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        log.debug("connect...");
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                log.debug("sending...");
                                Random r = new Random();
                                char c = 'a';
                                ByteBuf buffer = ctx.alloc().buffer();
                                for (int i = 0; i < 10; i++) {
                                    for (int j = 1; j <= r.nextInt(16)+1; j++) {
                                        buffer.writeByte((byte) c);
                                    }
                                    //buffer.writeByte(10);//10为\n
                                    buffer.writeByte(46);
                                    c++;
                                }
                                ctx.writeAndFlush(buffer);
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 1234).sync();
        channelFuture.channel().closeFuture().sync();
    }

}
