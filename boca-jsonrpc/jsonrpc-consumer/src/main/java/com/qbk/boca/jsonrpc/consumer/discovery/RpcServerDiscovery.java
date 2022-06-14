package com.qbk.boca.jsonrpc.consumer.discovery;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.googlecode.jsonrpc4j.ProxyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Rpc服务发现
 */
@Slf4j
@Component
public class RpcServerDiscovery {

    @Autowired
    private LoadBalancer loadBalancer;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 获取JsonRpcClient
     * @param serverName 服务端名称
     * @param serverClass 服务api类型
     */
    public <T> T getRpcServer(String serverName ,Class<T> serverClass){
        //获取服务列表
        List<ServiceInstance> instanceList = discoveryClient.getInstances(serverName);
        //通过负载均衡得到一个服务实例
        ServiceInstance instance = loadBalancer.getInstance(instanceList);
        if(Objects.isNull(instance)){
            throw new RuntimeException("获取rpc服务实例失败");
        }
        //获取rpc路径
        String path = serverClass.getAnnotation(JsonRpcService.class).value();
        URL url = null;
        //You can add authentication headers etc to this map
        Map<String, String> map = new HashMap<>();
        try {
            //拼接rpc全地址
            url = new URL(instance.getUri() + path);
            log.info("请求的rpc地址:{}",url);
        } catch (Exception e) {
            log.error("获取rpc URL异常",e);
        }
        JsonRpcHttpClient client = new JsonRpcHttpClient(url, map);
        return ProxyUtil.createClientProxy(getClass().getClassLoader(), serverClass, client);
    }
}