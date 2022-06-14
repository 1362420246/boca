package com.qbk.boca.jsonrpc.consumer.discovery;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 负载均衡
 */
public interface LoadBalancer {
    /**
     * 获取服务实例
     */
    ServiceInstance getInstance(List<ServiceInstance> serviceInstanceList);
}