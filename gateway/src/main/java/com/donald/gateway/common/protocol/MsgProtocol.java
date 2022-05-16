package com.donald.gateway.common.protocol;

import lombok.Data;


/**
 * @author donald
 * @date 2022/05/14
 */
@Data
public class MsgProtocol<T> {

    /**
     * 固定头部
     */
    private FixedHeader fixedHeader;
    /**
     * 消息体
     */
    private T body;
}
