package com.qbk.boca.mq.stream.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BocaMqStreamConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BocaMqStreamConsumerApplication.class,args);
    }
}
