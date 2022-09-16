package com.donald.common;

import com.donald.common.constant.Constants;
import com.donald.common.protocol.FixedHeader;
import com.donald.common.protocol.Message;
import com.donald.common.protocol.MsgDecoder;
import com.donald.common.protocol.MsgEncoder;
import com.donald.proto.Auth.LoginData;
import com.donald.proto.Base.Request;
import com.donald.proto.Enums;
import com.google.protobuf.Any;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.util.ReferenceCountUtil;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for MsgEncoder and MsgDecoder.
 *
 * @author donald
 * @date 2022/05/17
 */
public class CodecTest {

    private static final ByteBufAllocator ALLOCATOR = new UnpooledByteBufAllocator(false);

    @Mock
    private final ChannelHandlerContext ctx = Mockito.mock(ChannelHandlerContext.class);

    @Mock
    private final Channel channel = Mockito.mock(Channel.class);

    private final MsgDecoder decoder = new MsgDecoder();

    private final List<Object> out = new ArrayList<Object>();

    /**
     * MsgDecoder with an unrealistic max payload size of 1 byte.
     */
    private final MsgDecoder decoderLimitedMessageSize = new MsgDecoder(1);

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(ctx.channel()).thenReturn(channel);
        Mockito.when(ctx.alloc()).thenReturn(ALLOCATOR);
        Mockito.when(ctx.fireChannelRead(ArgumentMatchers.any())).then((Answer<ChannelHandlerContext>) invocation -> {
            out.add(invocation.getArguments()[0]);
            return ctx;
        });
    }

    @After
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

        Assertions.assertEquals(1, out.size());

        final Message decodedMessage = (Message) out.get(0);
        validateFixedHeaders(message.getFixedHeader(), decodedMessage.getFixedHeader());
    }

    @Test
    public void testMessage() throws Exception {

        final Message message = createDefaultMessage();

        ByteBuf byteBuf = MsgEncoder.doEncode(ctx, message);

        decoder.channelRead(ctx, byteBuf);

        Assertions.assertEquals(1, out.size());

        final Message decodedMessage = (Message) out.get(0);

        validateFixedHeaders(message.getFixedHeader(), decodedMessage.getFixedHeader());
        validatePayload(message.getPayload(), decodedMessage.getPayload());
    }

    @Test
    public void testConnectMessage() throws Exception {

        final Message message = createConnectMessage();

        ByteBuf byteBuf = MsgEncoder.doEncode(ctx, message);

        decoder.channelRead(ctx, byteBuf);

        Assertions.assertEquals(1, out.size());

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

        Assertions.assertEquals(1, out.size());

        Assertions.assertEquals(0, byteBuf.readableBytes());

        final Message decodedMessage = (Message) out.get(0);

        validateFixedHeaders(message.getFixedHeader(), decodedMessage.getFixedHeader());
        validateDecoderExceptionTooLargeMessage(decodedMessage);
    }

    private static void validateDecoderExceptionTooLargeMessage(Message message) {
        Assertions.assertNull(message.getPayload());
        assertTrue(message.getDecoderResult().isFailure());
        Throwable cause = message.getDecoderResult().cause();
        MatcherAssert.assertThat(cause, CoreMatchers.instanceOf(DecoderException.class));

        Assertions.assertTrue(cause.getMessage().contains("too large message:"));
    }

    private static void validatePayload(Object expected, Object actual) {

        Assertions.assertEquals(expected, actual);
    }

    private static void validateFixedHeaders(FixedHeader expected, FixedHeader actual) {
        assertEquals(expected.getMagic(), actual.getMagic());
        assertEquals(expected.getVersion(), actual.getVersion());
    }

    private static Message createDefaultMessage() {

        FixedHeader fixedHeader = new FixedHeader(Constants.MAGIC, Constants.VERSION, 0);

        /**
         * Request request = Request.newBuilder().setAction(0)
         *                 .build();
         * request.toByteArray().length = 0
         *
         * 这样就解析失败。
         */
        Request request = Request.newBuilder().setAction(Enums.ActionType.CONNECT)
                .build();

        return new Message(fixedHeader, request);
    }

    private static Message createConnectMessage() {

        FixedHeader fixedHeader = new FixedHeader(Constants.MAGIC, Constants.VERSION, 0);

        LoginData loginData = LoginData.newBuilder().setToken("12345678").build();

        Request request = Request.newBuilder().setAction(Enums.ActionType.CONNECT)
                .setData(Any.pack(loginData))
                .build();

        return new Message(fixedHeader, request);
    }
}
