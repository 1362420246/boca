package com.qbk.boca.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查看所有路由
 */
@Component
@AllArgsConstructor
public class MyRoutes {
    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;

    @PostConstruct
    public void init(){
        //所有路由
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        //通过配置文件 spring.cloud.Gateway.routes 配置的路由
        List<String> routeDefinitions = gatewayProperties.getRoutes().stream().map(RouteDefinition::getId).collect(Collectors.toList());
        /*
        [customer_filter_router,
        CompositeDiscoveryClient_BOCA-GATEWAY,
        CompositeDiscoveryClient_BOCA-ADMIN,
        CompositeDiscoveryClient_BOCA-EUREKA,
        CompositeDiscoveryClient_BOCA-DEMO,
        boca-demo,
        customer_gateway_route]
         */
        System.out.println(routes);
        /*
        [boca-demo,
        customer_gateway_route]
         */
        System.out.println(routeDefinitions);
    }

}
