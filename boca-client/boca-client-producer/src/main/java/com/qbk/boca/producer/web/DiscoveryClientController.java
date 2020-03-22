package com.qbk.boca.producer.web;

import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DiscoveryClient测试
 */
@RequestMapping("/client")
@RestController
public class DiscoveryClientController {

    /**
     * 表示通常可用于发现服务(如Netflix)的读操作
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/list")
    public BaseResult<?> getList(){
        //获取所有注册的客户端服务名
        final List<String> services = discoveryClient.getServices();
        return BaseResultUtil.ok(services);
    }

    @GetMapping("/get")
    public BaseResult<?> get(){
        //获取一个服务中的所有实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("boca-client-producer");
        System.out.println(instances);
        for (ServiceInstance service: instances) {
            System.out.println("-------可爱分隔符---------");
            System.out.println(service.getHost());
            System.out.println(service.getInstanceId());
            System.out.println(service.getMetadata());
            System.out.println(service.getPort());
            System.out.println(service.getScheme());
            System.out.println(service.getServiceId());
            System.out.println(service.getUri());
        }
        return BaseResultUtil.ok(instances);
    }
}
