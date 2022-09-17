package com.donald.gateway.server;

import com.donald.common.protocol.MsgDecoder;
import com.donald.common.protocol.MsgEncoder;
import com.donald.gateway.config.BaseConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.StandardSocketOptions;

/**
 * @author donald
 * @date 2022/05/07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayServer {

    private final BaseConfig baseConfig;
    private final TcpServerHandler tcpServerHandler;

    private EventLoopGroup boss;
    private EventLoopGroup worker;

    public void start() throws InterruptedException {

        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(NioChannelOption.of(StandardSocketOptions.SO_KEEPALIVE), true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_RCVBUF, 16 * 1024)
                .childOption(ChannelOption.SO_SNDBUF, 16 * 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline()
                                .addLast(new IdleStateHandler(baseConfig.getHeartbeat().getReadTimeout() / 1000,
                                        0, 0))
                                .addLast(new MsgDecoder())
                                .addLast(new MsgEncoder())
                                .addLast(tcpServerHandler);
                    }
                });

        ChannelFuture future = bootstrap.bind(baseConfig.getPort())
                .addListener((ChannelFutureListener) bindFuture -> {
                    if (bindFuture.isSuccess()) {

                        log.info("网关服务[{}]正在运行, port:{}", baseConfig.getServerId(), baseConfig.getPort());
                    } else {
                        bindFuture.channel().closeFuture().sync()
                                .addListener((ChannelFutureListener) closeFuture -> close());
                    }
                }).sync();

        future.channel().closeFuture().sync()
                .addListener((ChannelFutureListener) closeFuture -> close());

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    class ShutdownThread extends Thread {
        @Override
        public void run() {
            close();
        }
    }

    private void close() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();

        log.info("网关服务[{}]已停止", baseConfig.getServerId());

        System.exit(0);
    }
}
