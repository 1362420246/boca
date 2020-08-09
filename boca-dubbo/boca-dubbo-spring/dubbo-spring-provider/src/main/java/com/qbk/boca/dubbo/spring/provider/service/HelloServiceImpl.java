package com.qbk.boca.dubbo.spring.provider.service;

import com.qbk.boca.dubbo.spring.api.HelloService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello "+name+" !";
    }
}
