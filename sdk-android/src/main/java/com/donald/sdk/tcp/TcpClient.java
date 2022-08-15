package com.donald.sdk.tcp;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author donald
 * @date 2022/08/14
 */
public class TcpClient {

    private static final EventLoopGroup threadGroup = new NioEventLoopGroup();
}
