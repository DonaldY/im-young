package com.donald.sdk;

import com.donald.common.protocol.MsgDecoder;
import com.donald.common.protocol.MsgEncoder;
import com.donald.sdk.constant.Constants;
import com.donald.sdk.tcp.TcpClientChannelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author donald
 * @date 2022/08/14
 */
@Slf4j
public class TcpClient {

    private final EventLoopGroup threadGroup = new NioEventLoopGroup();
    private final TcpClientChannelHandler tcpClientChannelHandler = new TcpClientChannelHandler();

    public TcpClient() {

    }

    public void login() {

        // 1. 获取 Token
        // 2. 连接服务器
        connect();
    }

    /**
     * 连接服务器
     *
     * Tips： 重试
     * 1. 通过 iplist 获取 ip 和 port
     * 2. 尝试连接
     */
    public void connect() {
        Bootstrap client = createBootstrap();
        int retry = 0;
        while (retry < Constants.CONNECT_MAX_RETRY) {
            /*try {
                ChannelFuture channelFuture = client.connect(addressInstance.getIp(), addressInstance.getPort()).sync();
                if (channelFuture.isSuccess()) {
                    log.debug("与网关[{}]的连接已建立", addressInstance.getServerId());
                    // 注册网关到路由服务
                    online((SocketChannel) channelFuture.channel());
                    return;
                }
            } catch (Exception e) {
                ++retry;
            }*/
        }
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
