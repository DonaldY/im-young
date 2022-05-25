package com.donald.gateway.common.protocol;

import com.donald.gateway.common.Constants;
import com.donald.proto.Base;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @author donald
 * @date 2022/05/14
 */
public final class MsgDecoder extends ReplayingDecoder<MsgDecoder.DecoderState> {

    /**
     * 状态
     */
    enum DecoderState {
        READ_FIXED_HEADER,
        READ_PAYLOAD,
        BAD_MESSAGE,
    }

    private FixedHeader fixedHeader;
    private int bytesRemainingInVariablePart;

    private final int maxBytesInMessage;

    public MsgDecoder() {
        this(Constants.DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public MsgDecoder(int maxBytesInMessage) {

        super(DecoderState.READ_FIXED_HEADER);
        this.maxBytesInMessage = maxBytesInMessage;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {

        switch (state()) {
            case READ_FIXED_HEADER: try {

                fixedHeader = decodeFixedHeader(buffer);
                bytesRemainingInVariablePart = fixedHeader.getRemainingLen();

                if (bytesRemainingInVariablePart > maxBytesInMessage) {

                    buffer.skipBytes(actualReadableBytes());

                    throw new TooLongFrameException("too large message: " + bytesRemainingInVariablePart + " bytes");
                }

                checkpoint(DecoderState.READ_PAYLOAD);
                // fall through
            } catch (Exception cause) {

                checkpoint(DecoderState.BAD_MESSAGE);
                return;
            }

            case READ_PAYLOAD: try {

                final Result<MessageLite> decodedPayload = decodePayload(buffer, bytesRemainingInVariablePart);

                bytesRemainingInVariablePart -= decodedPayload.numberOfBytesConsumed;
                if (bytesRemainingInVariablePart != 0) {

                    throw new DecoderException("non-zero remaining payload bytes: " +
                                    bytesRemainingInVariablePart);
                }

                checkpoint(DecoderState.READ_FIXED_HEADER);

                Message message = new Message(fixedHeader, decodedPayload.value);
                fixedHeader = null;
                out.add(message);
                break;
            } catch (Exception cause) {

                checkpoint(DecoderState.BAD_MESSAGE);
                return;
            }

            case BAD_MESSAGE:
                // Keep discarding until disconnection.
                buffer.skipBytes(actualReadableBytes());
                break;

            default:
                // Shouldn't reach here.
                throw new Error();
        }
    }

    /**
     * 解码荷载
     *
     * 荷载使用 protobuf
     *
     * 可以参考： ProtobufDecoder
     *
     * @param buffer buffer
     * @param bytesRemainingInVariablePart 可用长度
     * @return 消息
     */
    private static Result<MessageLite> decodePayload(ByteBuf buffer, int bytesRemainingInVariablePart) {

        ByteBuf b = buffer.readRetainedSlice(bytesRemainingInVariablePart);

        final byte[] array;
        final int offset;
        if (b.hasArray()) {
            array = b.array();
            offset = b.arrayOffset() + b.readerIndex();
        } else {
            array = new byte[bytesRemainingInVariablePart];
            b.getBytes(b.readerIndex(), array, 0, bytesRemainingInVariablePart);
            offset = 0;
        }

        MessageLite payloadData;
        try {

            // 重点
            payloadData = Base.Request.getDefaultInstance()
                    .getParserForType().parseFrom(array, offset, bytesRemainingInVariablePart);

        } catch (InvalidProtocolBufferException ex) {

            throw new DecoderException("invalid protobuf for BaseData");
        }


        return new Result<>(payloadData, bytesRemainingInVariablePart);
    }

    /**
     +-------------------------------+
     | 7 | 6 | 5 | 4 | 3 | 2 | 1 | 0 |
     +-------------------------------+
     | 魔数 4bit      | 版本 4bit     |
     +-------------------------------+
     | 延续位 1bit | 数据体字节个数 7bit|
     +-------------------------------+
     */
    private static FixedHeader decodeFixedHeader(ByteBuf buffer) {

        // 读取未分配 1字节
        short b1 = buffer.readUnsignedByte();

        int magic = b1 >> 4;
        if (magic != Constants.MAGIC) {

            throw new IllegalArgumentException("magic number is illegal, " + magic);
        }

        int version = b1 & 0x0f;

        int remainingLength = 0;
        int multiplier = 1;
        short digit;
        int loops = 0;
        do {
            digit = buffer.readUnsignedByte();
            remainingLength += (digit & 127) * multiplier;
            multiplier *= 128;
            loops++;
        } while ((digit & 128) != 0 && loops < 4);

        // protocol limits Remaining Length to 4 bytes
        if (loops == 4 && (digit & 128) != 0) {

            throw new DecoderException("remaining length exceeds 4 digits.");
        }

        return new FixedHeader(magic, version, remainingLength);
    }

    private static final class Result<T> {

        private final T value;
        private final int numberOfBytesConsumed;

        Result(T value, int numberOfBytesConsumed) {
            this.value = value;
            this.numberOfBytesConsumed = numberOfBytesConsumed;
        }
    }
}
