package com.donald.gateway.common.protocol;

import lombok.Data;


/**
 * @author donald
 * @date 2022/05/14
 */
@Data
public class YoungProtocol<T> {

    /**
     * 固定头部
     */
    private MsgHeader msgHeader;
    /**
     * 消息体
     */
    private T body;
}
