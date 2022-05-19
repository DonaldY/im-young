package com.donald.gateway.server;

import com.donald.gateway.config.BaseConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author donald
 * @date 2022/05/19
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class TcpServerHandler extends SimpleChannelInboundHandler {

    private final ClientManager clientManager;
    private final BaseConfig baseConfig;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        log.info("客户端连接已建立");

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

        log.info("客户端连接断开");

        //offline(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {

        if (evt instanceof IdleStateEvent) {

            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

            if (idleStateEvent.state() == IdleState.READER_IDLE) {

                SocketChannel clientChannel = (SocketChannel) ctx.channel();

                Optional<ClientManager.ClientInstance> optional = clientManager.getClientId(clientChannel);

                optional.ifPresent(clientInstance -> log.info("心跳超时:{}", clientInstance.clientId()));

                // offline(ctx);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
    }
}
