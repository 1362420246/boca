package com.qbk.boca.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 自定义过滤器工厂
 *
 * 自定义过滤器有两种实现方式，一种是直接 实现GatewayFilter或GlobalFilter接口，
 * 另一种是 自定义过滤器工厂（继承AbstractGatewayFilterFactory类） , 选择自定义过滤器工厂的方式，可以在配置文件中配置过滤器了。
 *
 * 注意命名： xxxxGatewayFilterFactory
 */
@Component
public class CustomerGatewayFilterFactory
        extends AbstractGatewayFilterFactory<CustomerGatewayFilterFactory.Config> {

    /**
     * 返回有关参数数量和快捷解析顺序的提示。
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("enabled");
    }

    public CustomerGatewayFilterFactory() {
        super(Config.class);
        System.out.println("Loaded GatewayFilterFactory [CustomerGatewayFilterFactory]");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }
            exchange.getAttributes().put("countStartTime", System.currentTimeMillis());
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        Long startTime = exchange.getAttribute("countStartTime");
                        StringBuilder sb = new StringBuilder("自定义过滤器工厂  :")
                                .append(exchange.getRequest().getURI().getRawPath())
                                .append(": ")
                                .append(System.currentTimeMillis() - startTime)
                                .append("ms");
                        sb.append(" params:").append(exchange.getRequest().getQueryParams());
                        System.out.println(sb.toString());
                    })
            );
        };
    }

    public static class Config {
        /**
         * 控制是否开启统计
         */
        private boolean enabled;

        public Config() {}

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

}