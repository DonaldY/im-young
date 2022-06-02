package com.donald.gateway;

import com.donald.gateway.common.Constants;
import com.donald.gateway.common.protocol.FixedHeader;
import com.donald.gateway.common.protocol.Message;
import com.donald.gateway.common.protocol.MsgDecoder;
import com.donald.gateway.common.protocol.MsgEncoder;
import com.donald.proto.Base;
import com.donald.proto.Enums;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for MsgEncoder and MsgDecoder.
 *
 * @author donald
 * @date 2022/05/17
 */
public class CodecTest {

    private static final ByteBufAllocator ALLOCATOR = new UnpooledByteBufAllocator(false);

    @Mock
    private final ChannelHandlerContext ctx = mock(ChannelHandlerContext.class);

    @Mock
    private final Channel channel = mock(Channel.class);

    private final MsgDecoder decoder = new MsgDecoder();

    private final List<Object> out = new ArrayList<Object>();

    /**
     * MsgDecoder with an unrealistic max payload size of 1 byte.
     */
    private final MsgDecoder decoderLimitedMessageSize = new MsgDecoder(1);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(ctx.channel()).thenReturn(channel);
        when(ctx.alloc()).thenReturn(ALLOCATOR);
        when(ctx.fireChannelRead(any())).then((Answer<ChannelHandlerContext>) invocation -> {
            out.add(invocation.getArguments()[0]);
            return ctx;
        });
    }

    @AfterEach
    public void after() {
        for (Object o : out) {
            ReferenceCountUtil.release(o);
        }
        out.clear();
    }

    @Test
    public void testPingReqMessage() throws Exception {

        final Message message = createDefaultMessage();

        testMessageWithOnlyFixedHeader(message);
    }

    private void testMessageWithOnlyFixedHeader(Message message) throws Exception {
        ByteBuf byteBuf = MsgEncoder.doEncode(ctx, message);

        decoder.channelRead(ctx, byteBuf);

        assertEquals(1, out.size());

        final Message decodedMessage = (Message) out.get(0);
        validateFixedHeaders(message.getFixedHeader(), decodedMessage.getFixedHeader());
    }

    @Test
    public void testMessage() throws Exception {

        final Message message = createDefaultMessage();

        ByteBuf byteBuf = MsgEncoder.doEncode(ctx, message);

        decoder.channelRead(ctx, byteBuf);

        assertEquals(1, out.size());

        final Message decodedMessage = (Message) out.get(0);

        validateFixedHeaders(message.getFixedHeader(), decodedMessage.getFixedHeader());
        validatePayload(message.getPayload(), decodedMessage.getPayload());
    }

    @Test
    public void testMessageForTooLarge() throws Exception {

        final Message message = createDefaultMessage();

        ByteBuf byteBuf = MsgEncoder.doEncode(ctx, message);

        // TODO: 异常，不报错
        decoderLimitedMessageSize.channelRead(ctx, byteBuf);

        assertEquals(1, out.size());

        assertEquals(0, byteBuf.readableBytes());

        final Message decodedMessage = (Message) out.get(0);

        validateFixedHeaders(message.getFixedHeader(), decodedMessage.getFixedHeader());
        validateDecoderExceptionTooLargeMessage(decodedMessage);
    }

    private static void validateDecoderExceptionTooLargeMessage(Message message) {
        assertNull(message.getPayload());
    }

    private static void validatePayload(Object expected, Object actual) {

        assertEquals(expected, actual);
    }

    private static void validateFixedHeaders(FixedHeader expected, FixedHeader actual) {
        assertEquals(expected.getMagic(), actual.getMagic());
        assertEquals(expected.getVersion(), actual.getVersion());
    }

    private static Message createDefaultMessage() {

        FixedHeader fixedHeader = new FixedHeader(Constants.MAGIC, Constants.VERSION, 0);

        Base.Request request = Base.Request.newBuilder().setAction(Enums.ActionType.CONNECT)
                .build();

        return new Message(fixedHeader, request);
    }
}
