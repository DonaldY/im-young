package com.donald.gateway.common.protocol;

import io.netty.handler.codec.DecoderResult;

/**
 * @author donald
 * @date 2022/08/03
 */
public class MessageFactory {

    private MessageFactory() { }

    public static Message newInvalidMessage(FixedHeader fixedHeader, Throwable cause) {

        return new Message(fixedHeader, null, DecoderResult.failure(cause));
    }
}
