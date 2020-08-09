package com.qbk.boca.dubbo.spring.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * dubbo-spring-cloud
 */
@EnableDiscoveryClient
@SpringBootApplication
public class DubboSpringProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboSpringProviderApplication.class,args);    }
}
