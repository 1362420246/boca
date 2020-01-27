package com.qbk.boca.gateway.config;

import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义 局部 过滤器
 * 实现GatewayFilter接口
 */
@Configuration
public class FilterGroupConfig {

    /**
     * 自定义 GatewayFilter
     */
    @Bean
    public GatewayFilter customerGatewayFilter (){
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("first pre GatewayFilter ");
                exchange.getAttributes().put("countStartTime", Instant.now().toEpochMilli() );
                return chain.filter(exchange).then(
                        Mono.fromRunnable(() -> {
                            long startTime = exchange.getAttribute("countStartTime");
                            long endTime=(Instant.now().toEpochMilli() - startTime);
                            //返回该URI的原始路径组件。
                            System.out.println(exchange.getRequest().getURI().getRawPath() + ": " + endTime + "ms");
                        })
                );
            }
        };
    }

    /**
     * StripPrefixGatewayFilterFactory 过滤器
     * 该过滤器从请求中删除路径的第一部分，即前缀
     */
    @Bean
    public GatewayFilter stripPrefix(){
        final StripPrefixGatewayFilterFactory.Config config = new StripPrefixGatewayFilterFactory.Config();
        //设置StripPrefix=1表示从二级url路径转发
        config.setParts(1);
        return new StripPrefixGatewayFilterFactory().apply(config);
    }

    /**
     * 将自定义的GatewayFilter 注册到router中
     */
    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
        /*
        RouteLocator 路由定位器，顾名思义就是用来获取路由的方法。该路由定位器为顶级接口有多个实现类
        由定位器接口有三种实现方法：
            RouteDefinitionRouteLocator 基于路由定义的定位器
            CachingRouteLocator 基于缓存的路由定位器
            CompositeRouteLocator 基于组合方式的路由定位器
         */
        return builder.routes()
                //创建一个新的路由
                .route(r ->
                        //检查请求的路径是否与给定模式匹配的谓词
                        r.path("/customer/**")
                        //过滤器链
                        .filters(
                                //第一个自定义过滤器
                                f -> f.filter(customerGatewayFilter())
                                        //第二个StripPrefixGatewayFilterFactory 过滤器  可以自定义，也可以使用 GatewayFilterSpec 中的网关过滤器规范
                                        //.filter(stripPrefix())
                                        .stripPrefix(1)
                                        //第三个过滤器 添加响应头 使用的是AddResponseHeaderGatewayFilterFactory过滤器
                                        .addResponseHeader("X-Response-test", "test")
                                        //第四个过滤器 配置熔断，因为配置文件中的default-filters默认过滤器对这样配置路由方式不起作用，所以单独配置熔断，使用的也是HystrixGatewayFilterFactory过滤器
                                        .hystrix(config -> config
                                                .setName("myHystrix")
                                                .setFallbackUri("forward:/fallback")
                                )
                        )
                        // uri以lb://开头（lb代表从注册中心获取服务），后面接的就是你需要转发到的服务名称
                        .uri("lb://boca-demo")
                        .id("customer_filter_router")
                )
                .build();
    }
}
