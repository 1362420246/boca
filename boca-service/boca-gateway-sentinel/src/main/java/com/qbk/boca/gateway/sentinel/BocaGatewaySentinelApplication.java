package com.qbk.boca.gateway.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BocaGatewaySentinelApplication {
    public static void main(String[] args) {
        SpringApplication.run(BocaGatewaySentinelApplication.class,args);
    }
}
