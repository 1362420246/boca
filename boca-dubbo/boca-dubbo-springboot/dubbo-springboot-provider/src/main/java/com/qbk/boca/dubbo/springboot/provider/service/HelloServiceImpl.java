package com.qbk.boca.dubbo.springboot.provider.service;


import com.qbk.boca.dubbo.springboot.api.HelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 多注册中心、多协议、版本控制
 */
@DubboService(
        registry = {"shanghai","beijing"}, //多注册中心
        protocol = {"dubbo","rest"} , //多协议
        version = "1.0" //版本控制
)
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "[version1.0]Hello "+name+" !";
    }
}
