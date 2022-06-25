package com.donald.gateway.config;

import lombok.Data;

/**
 * @author donald
 * @date 2022/05/07
 */
@Data
public class HeartbeatConfig {
    /**
     * 发送心跳间隔
     */
    private Integer heartbeatInterval;
    /**
     * 读超时：默认10秒
     */
    private Integer readTimeout;

    public HeartbeatConfig() {
        this.heartbeatInterval = 2 * 6000;
        this.readTimeout = 30000;
    }
}
