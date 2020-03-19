package com.qbk.boca.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BocaNacosApplication {
    public static void main(String[] args) {
        SpringApplication.run(BocaNacosApplication.class,args);
    }
}
