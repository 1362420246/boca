package com.qbk.boca.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.qbk.boca.gateway.constant.GatewayConstant;
import com.qbk.boca.gateway.entity.LogDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * token全局过滤器
 */
@Slf4j
public class TokenGlobalFilter implements GlobalFilter {

    /**
     * 过滤的路径
     */
    private final List<String> IGNORE_PATH_LIST = Arrays.asList("/ailink-basic/user/login","/ailink-basic/user/logout","/user/login","/user/logout");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //过滤登陆接口
        if(IGNORE_PATH_LIST.contains(exchange.getRequest().getPath().value().toLowerCase())){
            return chain.filter(exchange);
        }
        //tonken校验
        if(!tokenCheck(exchange)){
            ServerHttpResponse response = exchange.getResponse();
            Map<String, Object> map = new HashMap<>();
            map.put("code", 400);
            map.put("message", "缺少token");
            byte[] datas = JSON.toJSONBytes(map);
            DataBuffer buffer = response.bufferFactory().wrap(datas);
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange);
    }

    /**
     * 校验token
     */
    private boolean tokenCheck(ServerWebExchange exchange){
        //判断token
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String token = headers.getFirst(GatewayConstant.AUTH_TOKEN_KEY);
        if (StringUtils.isBlank(token) || !token.startsWith(GatewayConstant.TOKEN_PROTOCOL_KEY)) {
            return false;
        }else {
            //TODO 此处从token中解出 用户
            LogDTO sysLog = exchange.getAttribute(GatewayConstant.LOG_ATTRIBUTES);
            if(Objects.nonNull(sysLog)){
                sysLog.setUserId(0);
                sysLog.setUserName("");
            }
        }
        return true;
    }
}
