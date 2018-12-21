package com.su.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    private String appId;
    private String secret;
    private String content_url;
    private String mchId;
    private String merKey;
    private String keyPath;
}
