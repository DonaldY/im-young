package com.donald.sdk.tcp;

import com.donald.proto.Base.Response;
import com.donald.proto.Enums.ActionType;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;

/**
 * @author donald
 * @date 2022/09/16
 */
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class TcpClientChannelHandler extends SimpleChannelInboundHandler<Response> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {
        ActionType actionType = response.getAction();

        /*ActionHandler actionHandler = actionHandlerFactory.getActionHandler(actionType);

        actionHandler.handle(request, ctx);*/
    }
}
