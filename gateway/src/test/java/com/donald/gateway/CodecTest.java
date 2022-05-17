package com.donald.gateway;

import com.donald.gateway.common.Constants;
import com.donald.gateway.common.protocol.FixedHeader;
import com.donald.gateway.common.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

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

    private final MqttDecoder mqttDecoder = new MqttDecoder();

    private final List<Object> out = new ArrayList<Object>();

    /**
     * MsgDecoder with an unrealistic max payload size of 1 byte.
     */
    private final MqttDecoder mqttDecoderLimitedMessageSize = new MqttDecoder(1);

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
    public void testMessageForTooLarge() {

    }

    private static Message createMessage() {

        FixedHeader fixedHeader = new FixedHeader(Constants.MAGIC, Constants.VERSION, 0);

        ByteBuf payload = ALLOCATOR.buffer();
        payload.writeBytes("whatever".getBytes(CharsetUtil.UTF_8));

        return new Message(fixedHeader, payload);
    }
}
