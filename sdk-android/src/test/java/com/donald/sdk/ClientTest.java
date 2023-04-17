package com.donald.sdk;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author donald
 * @date 2022/09/17
 */
@Slf4j
public class ClientTest {

    private TcpClient tcpClient;

    @Before
    public void setup() {
        tcpClient = new TcpClient();
    }

    @After
    public void after() {
        tcpClient.close();
    }

    @Test
    public void testCreateBootstrap() {
        Bootstrap bootstrap = tcpClient.createBootstrap();

        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9470).sync();
            if (channelFuture.isSuccess()) {
                log.info("网关连接已建立");
            }
        } catch (InterruptedException e) {
            log.info("连接 error：", e);
        }
    }
}
