package com.donald.gateway.common.protocol;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author donald
 * @date 2022/05/14
 */
@Data
@AllArgsConstructor
public class FixedHeader {

    /**
     +-------------------------------+
     | 7 | 6 | 5 | 4 | 3 | 2 | 1 | 0 |
     +-------------------------------+
     | 魔数 4bit      | 版本 4bit     |
     +-------------------------------+
     | 延续位 1bit | 数据体字节个数 7bit|
     +-------------------------------+
     */
    private final int magic;        // 魔数
    private final int version;      // 协议版本
    private final int remainingLen; // 剩余长度
}
