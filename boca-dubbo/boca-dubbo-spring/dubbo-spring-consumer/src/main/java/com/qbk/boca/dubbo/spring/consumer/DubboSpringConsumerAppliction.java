package com.qbk.boca.dubbo.spring.consumer;

import com.qbk.boca.dubbo.spring.api.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * dubbo-spring-cloud
 */
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class DubboSpringConsumerAppliction {
    public static void main(String[] args) {
        SpringApplication.run(DubboSpringConsumerAppliction.class,args);
    }

    @Reference
    private HelloService helloService;

    @GetMapping("/get")
    public String get(){
        return helloService.sayHello("qbk");
    }
}
