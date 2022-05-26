package com.donald.gateway.server.action;

import com.donald.proto.Base.Request;
import com.donald.proto.Enums.ActionType;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 心跳处理器
 *
 * @author donald
 * @date 2022/05/26
 */
@Slf4j
@Component
public class HeartbeatActionHandler implements ActionHandler {

    @Override
    public void handle(Request request, ChannelHandlerContext ctx) {

        log.info("收到心跳");
    }

    @Override
    public ActionType getActionType() {

        return ActionType.HEARTBEAT;
    }
}
