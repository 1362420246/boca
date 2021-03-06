package com.qbk.boca.dubbo.springboot.consumer;

import com.qbk.boca.dubbo.springboot.api.HelloService;
import com.qbk.boca.dubbo.springboot.api.QRespon;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.rpc.cluster.loadbalance.RoundRobinLoadBalance;
import org.apache.dubbo.rpc.cluster.support.FailfastCluster;
import org.apache.dubbo.rpc.cluster.support.FailoverCluster;
import org.apache.dubbo.rpc.service.GenericService;
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

    /**
     * 用于引用Dubbo服务的注释
     */
    @DubboReference(
            registry = {"shanghai","beijing"},//多注册中心
            version = "2.0",//版本控制
            loadbalance = RoundRobinLoadBalance.NAME , //负载均衡
            mock = "com.qbk.boca.dubbo.springboot.consumer.service.MockServiceImpl",//服务降级
            cluster = FailfastCluster.NAME , //集群容错，快速失败
            check = false , //启动检查 默认为true
            methods = { //方法级的配置 测试优先级
                    @Method( name= "sayHello" , timeout = 20000, retries = 2) //重试两次 每次20秒 ，最大超时时间40秒
            }
    )
    private HelloService helloService;

    @GetMapping("/get")
    public String get(String name){
        return helloService.sayHello(name);
    }

    @GetMapping("/getKryo")
    public QRespon getKryo(String name){
        return helloService.sayKryo(name);
    }

    /**
     * 泛化
     * 指定泛化接口的地址
     */
    @DubboReference(
            interfaceName = "com.qbk.boca.dubbo.springboot.provider.service.GeneralizationService" ,
            generic = true ,//是否启用泛型调用
            check = false //启动检查 默认为true
    )
    private GenericService genericService;

    @GetMapping("/getName")
    public String getName(String name) {
        /*
        泛化调用：
            * @param method 调用的方法
            * @param parameterTypes 参数类型列表
            * @param args 参数
         */
        return (String) genericService.$invoke(
                "getName",
                new String[]{"java.lang.String"},
                new Object[]{name}
        );
    }
}

