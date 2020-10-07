package com.qbk.seata.at.a;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.qbk.seata.at.a.mapper")
public class BocaSeataAtAdemoAppliction {

    public static void main(String[] args) {
        SpringApplication.run(BocaSeataAtAdemoAppliction.class);
    }
}
