package com.donald.sdk;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author donald
 * @date 2022/09/17
 */
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
        } catch (InterruptedException e) {

        }
    }
}
