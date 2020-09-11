package com.qbk.boca.dubbo.spring.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * dubbo-spring-cloud
 */
@EnableDiscoveryClient
@SpringBootApplication
public class DubboSpringConsumerAppliction {
    public static void main(String[] args) {
        SpringApplication.run(DubboSpringConsumerAppliction.class,args);
    }

}
