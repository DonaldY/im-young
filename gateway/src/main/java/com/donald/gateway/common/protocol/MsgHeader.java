package com.donald.gateway.common.protocol;


/**
 * @author donald
 * @date 2022/05/14
 */
public class MsgHeader {

    /**
     +-------------------------------+
     | 7 | 6 | 5 | 4 | 3 | 2 | 1 | 0 |
     +-------------------------------+
     | 魔数 4bit      | 版本 4bit     |
     +-------------------------------+
     | 延续位 1bit | 数据体字节个数 7bit|
     +-------------------------------+
     */
    private short magic;   // 魔数
    private short version; // 协议版本
    private int len;       // 数据长度
}
