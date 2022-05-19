package com.donald.gateway.utils;

import io.netty.channel.socket.SocketChannel;

/**
 * @author donald
 * @date 2022/05/19
 */
public class ChannelUtils {

    public static String getChannelId(SocketChannel socketChannel) {

        return socketChannel.id().asLongText();
    }
}
