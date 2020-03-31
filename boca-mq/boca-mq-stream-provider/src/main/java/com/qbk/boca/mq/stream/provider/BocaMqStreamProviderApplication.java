package com.qbk.boca.mq.stream.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BocaMqStreamProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(BocaMqStreamProviderApplication.class,args);
    }
}
