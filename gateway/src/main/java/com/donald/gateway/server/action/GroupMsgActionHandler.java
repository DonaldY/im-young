package com.donald.gateway.server.action;

import com.donald.proto.Base;
import com.donald.proto.Enums;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 群聊处理器
 *
 * @author donald
 * @date 2022/05/28
 */
@Slf4j
@Component
public class GroupMsgActionHandler implements ActionHandler {

    @Override
    public void handle(Base.Request request, ChannelHandlerContext ctx) {

        log.warn("啥事也不干!!!");
    }

    @Override
    public Enums.ActionType getActionType() {

        return Enums.ActionType.SEND_C2C_MSG;
    }
}
