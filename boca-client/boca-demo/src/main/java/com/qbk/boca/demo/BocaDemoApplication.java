package com.qbk.boca.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BocaDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BocaDemoApplication.class,args);
    }
}
