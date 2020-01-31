package com.qbk.boca.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BocaClientProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BocaClientProducerApplication.class,args);
    }
}
