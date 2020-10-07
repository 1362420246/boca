package com.qbk.seata.at.b;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.qbk.seata.at.b.mapper")
public class BocaSeataAtBdemoAppliction {
    public static void main(String[] args) {
        SpringApplication.run(BocaSeataAtBdemoAppliction.class);
    }
}
