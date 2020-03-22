package com.qbk.boca.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BocaSentinelApplication {
    public static void main(String[] args) {
        SpringApplication.run(BocaSentinelApplication.class,args);
    }
}
