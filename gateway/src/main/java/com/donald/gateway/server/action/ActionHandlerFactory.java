package com.donald.gateway.server.action;

import com.donald.proto.Enums.ActionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author donald
 * @date 2022/05/24
 */
@Component
@RequiredArgsConstructor
public class ActionHandlerFactory {

    private final List<ActionHandler> actionHandlers;

    public ActionHandler getActionHandler(ActionType type) throws Exception {

        for (ActionHandler actionHandler : actionHandlers) {

            if (type == actionHandler.getActionType()) {

                return actionHandler;
            }
        }

        // TODO: 统一异常
        throw new Exception();
    }
}
