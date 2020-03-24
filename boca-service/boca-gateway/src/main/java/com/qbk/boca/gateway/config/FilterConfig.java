package com.qbk.boca.gateway.config;

import com.qbk.boca.gateway.filter.LogGlobalFilter;
import com.qbk.boca.gateway.filter.TokenGlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 自定义全局过滤器
 * 实现GlobalFilter接口
 */
@Configuration
public class FilterConfig {

    @Bean
    @Order(0)
    public GlobalFilter filterFilterTest() {
        return new GlobalFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("first pre filter");
                exchange.getAttributes().put("startTime", System.currentTimeMillis());
                return chain.filter(exchange).then(Mono.fromRunnable(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("first post filter");
                        Long startTime = exchange.getAttribute("startTime");
                        long executeTime = (System.currentTimeMillis() - startTime);
                        System.out.println("url: " + exchange.getRequest().getURI());
                        System.out.println("耗时： " + executeTime);
                        System.out.println("状态码： " + Objects.requireNonNull(exchange.getResponse().getStatusCode()).value());
                    }
                }));
            }
        };
    }

    @Bean
    @Order(1)
    public GlobalFilter secondFilterTest() {
        return (exchange, chain) -> {
            System.out.println("second pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("second post filter");
            }));
        };
    }

    /**
     * token过滤器
     */
    @Bean
    public GlobalFilter tokenToken() {
        return new TokenGlobalFilter();
    }

    /**
     * log过滤器
     */
    @Bean
    public GlobalFilter logGlobalFilter() {
        return new LogGlobalFilter();
    }
}
