package com.qbk.boca.jsonrpc.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * jsonrpc4j 生产端
 */
@EnableDiscoveryClient
@SpringBootApplication
public class JsonrpcProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(JsonrpcProviderApplication.class,args);
    }
}
