package com.qbk.boca.demo.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义负载均衡策略
 * 仿照 RoundRobinRule
 */
public class BocaRule extends AbstractLoadBalancerRule {

    /**
     * 下一个服务器循环计数器
     */
    private AtomicInteger nextServerCyclicCounter =  new AtomicInteger(0);

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }

    /**
     * 从ILoadBalancer 中选择一个service
     * @return 如果没有返回null
     */
    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    private Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        //可用的服务
        List<Server> upList = lb.getReachableServers();
        //所有的服务
        List<Server> allList = lb.getAllServers();
        if ((allList.size() == 0) || (upList.size() == 0) ) {
            return null;
        }
        int upCount = upList.size();
        //获取下标
        int nextServerIndex = incrementAndGetModulo(upCount);
        Server server = upList.get(nextServerIndex);
        //判断可用
        if (server.isAlive() && (server.isReadyToServe())) {
            return server;
        }else {
            return null;
        }
    }

    /**
     * cas 做的自旋锁
     */
    private int incrementAndGetModulo(int modulo) {
        for (;;) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next)){
                return next;
            }
        }
    }

}
