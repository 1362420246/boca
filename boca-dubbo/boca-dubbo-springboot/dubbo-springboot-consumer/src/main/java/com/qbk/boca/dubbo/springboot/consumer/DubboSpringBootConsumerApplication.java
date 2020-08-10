package com.qbk.boca.dubbo.springboot.consumer;

import com.qbk.boca.dubbo.springboot.api.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * dubbo-spring-boot
 */
@RestController
@SpringBootApplication
public class DubboSpringBootConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboSpringBootConsumerApplication.class,args);
    }

    //多注册中心、版本控制
    @DubboReference(registry = {"shanghai","beijing"},version = "2.0")
    private HelloService helloService;

    @GetMapping("/get")
    public String get(String name){
        return helloService.sayHello(name);
    }
}

