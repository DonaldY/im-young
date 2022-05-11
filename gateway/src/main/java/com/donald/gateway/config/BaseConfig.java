package com.donald.gateway.config;

import com.donald.gateway.common.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author donald
 * @date 2022/05/07
 */
@Data
@Configuration
@ConfigurationProperties(prefix = BaseConfig.CONFIG_PREFIX)
public class BaseConfig {

    public static final String CONFIG_PREFIX = "im";

    /**
     * 服务Id
     */
    private String serverId;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 最大在线数
     */
    private Integer maxOnlineNum;
    /**
     * 连接检查时间
     */
    private Integer connectCheckTime;
    /**
     * 心跳配置
     */
    private HeartbeatConfig heartbeat;

    public BaseConfig() {

        this.connectCheckTime = Constants.DEFAULT_CONNECT_CHECK_TIME;
        this.maxOnlineNum = Constants.DEFAULT_MAX_ONLINE_NUM;
        this.heartbeat = new HeartbeatConfig();
    }
}
