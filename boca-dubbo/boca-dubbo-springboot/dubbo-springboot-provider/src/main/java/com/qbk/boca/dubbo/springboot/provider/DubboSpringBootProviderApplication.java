package com.qbk.boca.dubbo.springboot.provider;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * dubbo-spring-boot
 */
@DubboComponentScan(basePackages = "com.qbk.boca.dubbo.springboot.provider.service")
@SpringBootApplication
public class DubboSpringBootProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboSpringBootProviderApplication.class, args);
    }

}