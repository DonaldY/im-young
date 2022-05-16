package com.donald.gateway.common;

/**
 * @author donald
 * @date 2022/05/07
 */
public interface Constants {

    /**
     * 默认最大在线数
     */
    int DEFAULT_MAX_ONLINE_NUM = 300000;
    /**
     * 默认连接检查时间
     */
    int DEFAULT_CONNECT_CHECK_TIME = 3000;

    /**
     * 默认最大消息, 单位 B
     */
    int DEFAULT_MAX_BYTES_IN_MESSAGE = 8092;
    /**
     * 魔数
     */
    int MAGIC = 0x4;
    /**
     * 版本
     */
    int VERSION = 0x1;
}
