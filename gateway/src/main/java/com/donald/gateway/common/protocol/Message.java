package com.donald.gateway.common.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author donald
 * @date 2022/05/17
 */
@Data
@AllArgsConstructor
public class Message {

    private final FixedHeader fixedHeader;
    private final Object payload;
}
