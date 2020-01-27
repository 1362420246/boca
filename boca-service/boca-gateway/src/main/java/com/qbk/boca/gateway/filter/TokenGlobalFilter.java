package com.qbk.boca.gateway.filter;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * token全局过滤器
 */
public class TokenGlobalFilter implements GlobalFilter {

    private final String AUTH_TOKEN_KEY = "Authorization";
    private final String TOKEN_PROTOCOL_KEY = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("thirdly pre filter");
        //TODO 过滤登陆接口
//        HttpHeaders headers = exchange.getRequest().getHeaders();
//        String token = headers.getFirst(AUTH_TOKEN_KEY);
//        if (StringUtils.isBlank(token) || !token.startsWith(TOKEN_PROTOCOL_KEY)) {
//            ServerHttpResponse response = exchange.getResponse();
//            Map<String, Object> map = new HashMap<>();
//            map.put("code", 400);
//            map.put("message", "缺少token");
//            byte[] datas = JSON.toJSONBytes(map);
//            DataBuffer buffer = response.bufferFactory().wrap(datas);
//            response.setStatusCode(HttpStatus.OK);
//            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//            return response.writeWith(Mono.just(buffer));
//        }
        return chain.filter(exchange);
    }

}
