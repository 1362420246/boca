package com.qbk.boca.gateway.sentinel.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 全局过滤器配置
 */
@Slf4j
@Configuration
public class FilterConfig {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    /**
     * 依赖注入
     */
    public FilterConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                        ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

//    /**
//     * 默认限流后异常返回
//     */
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
//        // Register the block exception handler for Spring Cloud Gateway.
//        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
//    }

    /**
     * 配置限流后异常处理
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GatewayBlockExceptionHandler gatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new FilterConfig.GatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    /**
     * 限流过滤器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    /**
     * 加载限流规则
     */
    @PostConstruct
    public void doInit() {
        initGatewayRules();
        initCustomizedApis();
    }

    /**
     * 配置路由限流规则
     */
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        //配置注册服务的路由的流控
        rules.add(new GatewayFlowRule("CompositeDiscoveryClient_boca-demo-sentinel")
                // 限流阈值
                .setCount(10)
        );
        //配置自定义的路由的流控
        rules.add(new GatewayFlowRule("customized_api")
                .setCount(1)
        );
        GatewayRuleManager.loadRules(rules);
    }
    /**
     * 配置api限流规则
     */
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition api1 = new ApiDefinition("customized_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    // article完全匹配
                    add(new ApiPathPredicateItem().setPattern("/boca-demo-sentinel/test"));
                    // /开头的
                    add(new ApiPathPredicateItem().setPattern("/boca-demo-sentinel/**")
                            .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_PREFIX));
                }});
        definitions.add(api1);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    /**
     * 自定义流后异常处理
     * 参考 SentinelGatewayBlockExceptionHandler
     * 只需要修改writeResponse方法
     */
    static class GatewayBlockExceptionHandler implements WebExceptionHandler {
        private List<ViewResolver> viewResolvers;
        private List<HttpMessageWriter<?>> messageWriters;

        public GatewayBlockExceptionHandler(List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer) {
            this.viewResolvers = viewResolvers;
            this.messageWriters = serverCodecConfigurer.getWriters();
        }

        private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            byte[] datas = "{\"code\":429,\"message\":\"网关限流了\"}".getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(datas);
            return serverHttpResponse.writeWith(Mono.just(buffer));
        }

        @Override
        public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
            if (exchange.getResponse().isCommitted()) {
                return Mono.error(ex);
            }
            // This exception handler only handles rejection by Sentinel.
            if (!BlockException.isBlockException(ex)) {
                return Mono.error(ex);
            }
            return handleBlockedRequest(exchange, ex)
                    .flatMap(response -> writeResponse(response, exchange));
        }

        private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
            return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
        }

        private final Supplier<ServerResponse.Context> contextSupplier = () -> new ServerResponse.Context() {
            @Override
            public List<HttpMessageWriter<?>> messageWriters() {
                return GatewayBlockExceptionHandler.this.messageWriters;
            }

            @Override
            public List<ViewResolver> viewResolvers() {
                return GatewayBlockExceptionHandler.this.viewResolvers;
            }
        };
    }
}
