package com.qbk.boca.dubbo.spring.consumer.controller;

import com.qbk.boca.dubbo.spring.api.HelloService;
import com.qbk.boca.dubbo.spring.api.dto.ParamDTO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Reference
    private HelloService helloService;

    @GetMapping("/get")
    public String get(String name ){
        ParamDTO dto = new ParamDTO();
        dto.setName(name);
        return helloService.sayHello(dto);
    }
}
