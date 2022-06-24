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

        // 载荷长度
        int payloadBufferSize = payload.toByteArray().length;
        // 固定头部长度
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

    /**
     * 根据载荷长度，得到可变长度值
     *
     * Tips: 这块数据长度 <= 256M
     *       即最多 4字节： 2^7 * 2^7 * 2^7 * 2^8 = 256M
     *       最高位：1 表示有下一个字节存在，后 7 位数据位
     *
     *       因为写入消息时候就限制了消息体的长度了
     *
     * @param buf buf
     * @param num 载荷长度： 多少个字节
     */
    private static void writeVariableLengthInt(ByteBuf buf, int num) {
        // 每次写一个字节
        do {
            int digit = num % 128;
            num /= 128;
            if (num > 0) {
                digit |= 0x80;    // 0x80 = 10000000, 异运算后得到 1xxxxxxx
            }
            buf.writeByte(digit); // 写入 1字节
        } while (num > 0);
    }

    /**
     * 计算可变长度的值
     *
     * @param num 载荷长度： 多少个字节
     * @return 长度
     */
    private static int getVariableLengthInt(int num) {
        int count = 0;
        do {
            num /= 128;
            count++;
        } while (num > 0);
        return count;
    }
}
