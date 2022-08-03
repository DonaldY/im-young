package com.donald.gateway.common.protocol;

import io.netty.handler.codec.DecoderResult;
import lombok.Data;

/**
 * @author donald
 * @date 2022/05/17
 */
@Data
public class Message {

    private final FixedHeader fixedHeader;
    private final Object payload;
    private final DecoderResult decoderResult;

    public Message(FixedHeader fixedHeader, Object payload) {
        this(fixedHeader, payload, DecoderResult.SUCCESS);
    }

    public Message(FixedHeader fixedHeader, Object payload, DecoderResult decoderResult) {

        this.fixedHeader = fixedHeader;
        this.payload = payload;
        this.decoderResult = decoderResult;
    }
}
