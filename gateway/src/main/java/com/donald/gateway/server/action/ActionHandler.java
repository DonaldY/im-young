package com.donald.gateway.server.action;

import com.donald.proto.Base.Request;
import com.donald.proto.Enums.ActionType;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author donald
 * @date 2022/05/24
 */
public interface ActionHandler {
    /**
     * 处理
     */
    void handle(Request request, ChannelHandlerContext ctx);
    /**
     * 获取动作类型
     * @return 类型
     */
    ActionType getActionType();
}
