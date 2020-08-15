package com.qbk.boca.dubbo.springboot.provider.service;


import com.qbk.boca.dubbo.springboot.api.HelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.cluster.support.FailoverCluster;

/**
 * 多注册中心、多协议、版本控制
 */
@DubboService(
        registry = {"shanghai","beijing"},
        protocol = {"dubbo","rest"} ,
        version = "2.0",
        cluster = FailoverCluster.NAME , //容错策略 ， 2.7.5以后的版本不生效
        retries = 5 //重试次数 ，默认两次。
)
public class HelloServiceImpl2 implements HelloService {

    @Override
    public String sayHello(String name) {
        return "[version2.0]Hello "+name+" !";
    }
}
