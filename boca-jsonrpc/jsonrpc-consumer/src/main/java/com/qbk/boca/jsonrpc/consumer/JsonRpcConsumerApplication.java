package com.qbk.boca.jsonrpc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * jsonrpc4j 消费端
 */
@EnableDiscoveryClient
@SpringBootApplication
public class JsonRpcConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(JsonRpcConsumerApplication.class,args);
    }
}
