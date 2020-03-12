package com.qbk.boca.nacos.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置文件
 * 注解@ConfigurationProperties 不添加 @RefreshScope 也可以自动刷新属性
 * 因为 ConfigurationPropertiesRebinder
 */
@ConfigurationProperties(prefix="boca.param")
@Data
@Component
public class LocalCacheProperties {
    private String name;
    private Integer age;
}
