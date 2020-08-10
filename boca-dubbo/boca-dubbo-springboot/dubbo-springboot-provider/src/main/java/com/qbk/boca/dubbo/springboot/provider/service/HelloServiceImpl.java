package com.qbk.boca.dubbo.springboot.provider.service;


import com.qbk.boca.dubbo.springboot.api.HelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 多注册中心、版本控制
 */
//@DubboService
@DubboService(registry = {"shanghai","beijing"},version = "1.0")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "[version1.0]Hello "+name+" !";
    }
}
