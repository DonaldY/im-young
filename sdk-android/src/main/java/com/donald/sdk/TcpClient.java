package com.donald.sdk;

import com.donald.common.protocol.MsgDecoder;
import com.donald.common.protocol.MsgEncoder;
import com.donald.sdk.tcp.TcpClientChannelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author donald
 * @date 2022/08/14
 */
public class TcpClient {

    private final EventLoopGroup threadGroup = new NioEventLoopGroup();
    private final TcpClientChannelHandler tcpClientChannelHandler = new TcpClientChannelHandler();

    public TcpClient() {

    }

    public void connect() {

        Bootstrap client = createBootstrap();

    }

    public Bootstrap createBootstrap() {
        Bootstrap client = new Bootstrap();
        client.group(threadGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline()
                                .addLast(new MsgDecoder())
                                .addLast(new MsgEncoder())
                                .addLast(tcpClientChannelHandler);
                    }
                });
        return client;
    }

    public void close() {
        this.threadGroup.shutdownGracefully();
    }
}
