package com.donald.gateway.common.protocol;

import com.donald.proto.Base;
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
public class MsgEncoder extends MessageToMessageEncoder<Message> {

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
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) {

        out.add(doEncode(ctx, msg));
    }

    public static ByteBuf doEncode(ChannelHandlerContext ctx, Message msg) {

        FixedHeader header = msg.getFixedHeader();
        Base.Request payload = (Base.Request) msg.getPayload();

        int payloadBufferSize = payload.toByteArray().length;
        int fixedHeaderBufferSize = 1 + getVariableLengthInt(payloadBufferSize);
        ByteBuf buf = ctx.alloc().buffer(fixedHeaderBufferSize + payloadBufferSize);

        buf.writeByte(getFixedHeaderByte1(header));
        writeVariableLengthInt(buf, payloadBufferSize);
        buf.writeBytes(payload.toByteArray());

        return buf;
    }

    private static int getFixedHeaderByte1(FixedHeader header) {
        int ret = 0;
        ret |= header.getMagic() << 4;
        ret |= header.getVersion();

        return ret;
    }

    private static void writeVariableLengthInt(ByteBuf buf, int num) {
        do {
            int digit = num % 128;
            num /= 128;
            if (num > 0) {
                digit |= 0x80;
            }
            buf.writeByte(digit);
        } while (num > 0);
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
