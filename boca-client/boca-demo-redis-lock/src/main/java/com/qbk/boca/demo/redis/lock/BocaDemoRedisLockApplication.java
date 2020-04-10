package com.qbk.boca.demo.redis.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * redis分布式锁
 */
@SpringBootApplication
public class BocaDemoRedisLockApplication {
    public static void main(String[] args) {
        SpringApplication.run(BocaDemoRedisLockApplication.class,args);
    }
}
