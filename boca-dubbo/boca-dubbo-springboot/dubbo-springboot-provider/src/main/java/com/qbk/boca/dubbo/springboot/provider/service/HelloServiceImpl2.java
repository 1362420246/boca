package com.qbk.boca.dubbo.springboot.provider.service;


import com.qbk.boca.dubbo.springboot.api.HelloService;
import com.qbk.boca.dubbo.springboot.api.QRespon;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.cluster.support.FailoverCluster;

/**
 * 多注册中心、多协议、版本控制、容错
 */
@DubboService(
        registry = {"shanghai","beijing"},
        protocol = {"dubbo","rest"} ,
        version = "2.0",
        cluster = FailoverCluster.NAME , //容错策略 ， 并行策略2.7.5以后的版本不生效
        retries = 5 //重试次数 ，默认两次。
)
public class HelloServiceImpl2 implements HelloService {

    @Override
    public String sayHello(String name) {
        return "[version2.0]Hello "+name+" !";
    }

    @Override
    public QRespon sayKryo(String name) {
        QRespon respon = new QRespon();
        respon.setData("Kryo:" + name);
        return respon;
    }
}
