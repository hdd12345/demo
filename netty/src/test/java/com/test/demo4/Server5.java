package com.test.demo4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hdd
 * @date 2021-07-30
 */
@Slf4j
public class Server5 {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        /**
                         * maxFrameLength 数据包最大长度，超过会报错
                         * lengthFieldOffset 长度偏移量
                         * lengthFieldLength 长度的长度
                         * lengthAdjustment 长度偏移量矫正
                         * initialBytesToStrip 丢弃的字节数
                         */
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,0,1,0,1));
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
