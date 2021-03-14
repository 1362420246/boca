package com.qbk.boca.demo.redis.lock.annotation;

import java.lang.annotation.*;

/**
 * 注解分布式锁
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DistributedLock {

    String key() default "";

    /** 锁等待，默认5s */
    int waitTime() default 5;

    /** 锁超时，默认30s */
    int leaseTime() default 30;





}
