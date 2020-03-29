package com.qbk.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 开启Hystrix
 * EnableHystrix注解就是EnableCircuitBreaker注解
 */
//@EnableCircuitBreaker
@EnableHystrix
@SpringBootApplication
public class BocaDemoHystrixApplication {
    public static void main(String[] args) {
        SpringApplication.run(BocaDemoHystrixApplication.class,args);
    }
}
