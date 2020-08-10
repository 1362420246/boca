package com.qbk.boca.dubbo.springboot.provider.service;


import com.qbk.boca.dubbo.springboot.api.HelloService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 多注册中心、多协议、版本控制
 */
@DubboService(
        registry = {"shanghai","beijing"},
        protocol = {"dubbo","rest"} ,
        version = "2.0")
public class HelloServiceImpl2 implements HelloService {

    @Override
    public String sayHello(String name) {
        return "[version2.0]Hello "+name+" !";
    }
}
