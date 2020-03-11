package com.qbk.boca.demo.controller;

import com.qbk.boca.demo.api.RibbonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试负载均衡
 */
@RestController
public class RibbonContoller {

    @Autowired
    private RibbonApi ribbonApi;

    @GetMapping("/get/port")
    public String getPort(){
        final String port = ribbonApi.getPort();
        System.out.println("生产者的端口是:" + port);
        return port;
    }
}
