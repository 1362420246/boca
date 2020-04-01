package com.qbk.boca.mq.stream.concurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Cloud Stream 多消费者

 */
@SpringBootApplication
public class BocaMqStreamConcurrencyApplication {
    public static void main(String[] args) {
        SpringApplication.run(BocaMqStreamConcurrencyApplication.class,args);
    }
}
