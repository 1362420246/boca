package com.qbk.boca.jsonrpc.consumer.config;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcClientProxyCreator;
import com.qbk.boca.jsonrpc.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * jsonrpc客户端配置
 */
@Slf4j
@Configuration
public class JsonRpcConfig {

    /**
     * @param url 生产端rpc地址
     */
    @Bean
    @ConditionalOnProperty(value = {"rpc.url"})
    public AutoJsonRpcClientProxyCreator rpcClientProxyCreator(@Value("${rpc.url}") String url){
        AutoJsonRpcClientProxyCreator creator = new AutoJsonRpcClientProxyCreator();
        try {
            creator.setBaseUrl(new URL(url));
        } catch (MalformedURLException e) {
            log.error("创建rpc服务地址错误", e);
            e.printStackTrace();
        }
        //扫描rpc所在包
        creator.setScanPackage(UserService.class.getPackage().getName());
        return creator;
    }
}
