package com.donald.sdk.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.ScheduledFuture;

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


}
