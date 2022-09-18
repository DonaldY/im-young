package com.donald.sdk.tcp.action;

import com.donald.proto.Base.Response;
import com.donald.proto.Enums.ActionType;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author donald
 * @date 2022/09/17
 */
public interface ActionHandler {
    /**
     * 处理
     */
    void handle(Response response, ChannelHandlerContext ctx);
    /**
     * 获取动作类型
     * @return 类型
     */
    ActionType getActionType();
}
