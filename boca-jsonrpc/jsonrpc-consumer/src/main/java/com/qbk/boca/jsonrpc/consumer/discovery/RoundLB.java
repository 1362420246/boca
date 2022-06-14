package com.qbk.boca.jsonrpc.consumer.discovery;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 负载均衡-循环策略
 */
@Component
public class RoundLB implements LoadBalancer {

    private AtomicInteger invokeTimes = new AtomicInteger(0);

    private int getAndIncrement() {
        int current, next;
        do {
            current = this.invokeTimes.get();
            //防止调用次数过多
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        } while (!this.invokeTimes.compareAndSet(current, next));
        return next;
    }

    @Override
    public ServiceInstance getInstance(List<ServiceInstance> serviceInstanceList) {
        int index = getAndIncrement() % serviceInstanceList.size();
        return serviceInstanceList.get(index);
    }
}