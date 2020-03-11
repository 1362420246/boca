package com.qbk.boca.producer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 生产者测试接口
 * 提供测试负载均衡接口
 */
@RequestMapping("/ribbon")
@RestController
public class RibbonApi {


    @Autowired
    Environment environment;

    @GetMapping("/port")
    public String getPort(){
        //模拟其中一个生产者服务 响应慢，观察客户端在使用WeightedResponseTimeRule策略时候的权重
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return environment.getProperty("local.server.port");
    }
}
