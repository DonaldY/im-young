package com.donald.sdk.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
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
    ScheduledFuture<?> scheduledFuture;

    ChannelHandlerContext ctx;

    public TcpClient() {
    }

    public void connect() {

    }

    void reconnect() {

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
