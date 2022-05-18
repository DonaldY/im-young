package com.donald.gateway.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author donald
 * @date 2022/05/18
 */
@ChannelHandler.Sharable
public class MsgEncoder extends MessageToMessageEncoder<MsgProtocol<ByteBuf>> {

    /**
     +-------------------------------+
     | 7 | 6 | 5 | 4 | 3 | 2 | 1 | 0 |
     +-------------------------------+
     | 魔数 4bit      | 版本 4bit     |
     +-------------------------------+
     | 延续位 1bit | 数据体字节个数 7bit|
     +-------------------------------+
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, MsgProtocol<ByteBuf> msg, List<Object> out) {
        FixedHeader header = msg.getFixedHeader();
        ByteBuf payload = msg.getBody();

        int payloadBufferSize = payload.readableBytes();
        int fixedHeaderBufferSize = 1 + getVariableLengthInt(payloadBufferSize);
        ByteBuf buf = ctx.alloc().buffer(fixedHeaderBufferSize + payloadBufferSize);
        buf.writeByte(getFixedHeaderByte1(header));
        buf.writeBytes(payload);

        out.add(buf);
    }

    private static int getFixedHeaderByte1(FixedHeader header) {
        int ret = 0;
        ret |= header.getMagic() << 4;
        ret |= header.getVersion();

        return ret;
    }

    private static int getVariableLengthInt(int num) {
        int count = 0;
        do {
            num /= 128;
            count++;
        } while (num > 0);
        return count;
    }
}
