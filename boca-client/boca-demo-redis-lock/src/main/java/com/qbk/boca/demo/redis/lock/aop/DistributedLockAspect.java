package com.qbk.boca.demo.redis.lock.aop;

import com.qbk.boca.core.exception.BasicException;
import com.qbk.boca.demo.redis.lock.annotation.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Configuration
public class DistributedLockAspect {

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(distributedLock)")
    public void pointcut(DistributedLock distributedLock) {}

    @Around("pointcut(distributedLock)")
    public Object around(ProceedingJoinPoint jp, DistributedLock distributedLock) throws Throwable {
        String key = distributedLock.key();

        // 如果没有设置key，则使用方法类路径作为key
        if (StringUtils.isEmpty(key)) {
            key = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
        }
        RLock lock = redissonClient.getLock(key);
        // 尝试加锁
        if (lock.tryLock(distributedLock.waitTime(),distributedLock.leaseTime(),TimeUnit.SECONDS)) {
            try {
                return jp.proceed();
            } finally {
                lock.unlock();
            }
        } else {
            log.error("分布式锁获取失败");
            throw new BasicException("锁获取失败");
        }
    }
}
