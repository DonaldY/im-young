package com.donald.sdk.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author donald
 * @date 2022/08/14
 */
public class TcpClient {

    private static final EventLoopGroup threadGroup = new NioEventLoopGroup();

    public TcpClient() {
    }

    public void connect() {
        Bootstrap client = new Bootstrap();
        client.group(threadGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
               // .handler(new TcpClientChannelInitializer(this));
    }

    static class MessageSet {

        private final Set<Long> messageSet = Collections.synchronizedSet(new HashSet<>());

        void setMessage(Long messageId) {
            messageSet.add(messageId);
        }

        void clearMessage(Long messageId) {
            messageSet.remove(messageId);
        }

        Boolean exist(Long messageId) {
            return messageSet.contains(messageId);
        }
    }
}
