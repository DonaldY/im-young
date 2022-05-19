package com.donald.gateway.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBufUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author donald
 * @date 2022/05/17
 */
@Data
@AllArgsConstructor
public class Message implements ByteBufHolder {

    private final FixedHeader fixedHeader;

    private final ByteBuf payload;

    @Override
    public ByteBuf content() {
        return ByteBufUtil.ensureAccessible(payload);
    }

    @Override
    public Message copy() {
        return replace(content().copy());
    }

    @Override
    public Message duplicate() {
        return replace(content().duplicate());
    }

    @Override
    public Message retainedDuplicate() {
        return replace(content().retainedDuplicate());
    }

    @Override
    public Message replace(ByteBuf content) {
        return new Message(fixedHeader, content);
    }

    @Override
    public int refCnt() {
        return content().refCnt();
    }

    @Override
    public Message retain() {
        content().retain();
        return this;
    }

    @Override
    public Message retain(int increment) {
        content().retain(increment);
        return this;
    }

    @Override
    public Message touch() {
        content().touch();
        return this;
    }

    @Override
    public Message touch(Object hint) {
        content().touch(hint);
        return this;
    }

    @Override
    public boolean release() {
        return content().release();
    }

    @Override
    public boolean release(int decrement) {
        return content().release(decrement);
    }
}
