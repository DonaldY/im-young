package com.donald.gateway.server.action;

import com.donald.proto.Base.Request;
import com.donald.proto.Enums.ActionType;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 连接处理器 / 上线处理
 *
 * @author donald
 * @date 2022/05/29
 */
@Slf4j
@Component
public class OnlineActionHandler implements ActionHandler {

    @Override
    public void handle(Request request, ChannelHandlerContext ctx) {

        log.warn("啥事也不干!!!");
    }

    @Override
    public ActionType getActionType() {

        return ActionType.CONNECT;
    }
}
