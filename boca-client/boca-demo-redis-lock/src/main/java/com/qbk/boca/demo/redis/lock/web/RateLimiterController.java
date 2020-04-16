package com.qbk.boca.demo.redis.lock.web;

import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Redisson 限流器（RateLimiter）
 */
@RestController
public class RateLimiterController {

    @Autowired
    private RedissonClient redissonClient;

    private RRateLimiter rateLimiter ;

    @PostConstruct
    public void init(){

        rateLimiter = redissonClient.getRateLimiter("rate_limiter");

        // 初始化
        // 最大流速 = 每分钟产生3个令牌
        rateLimiter.trySetRate(RateType.OVERALL, 3, 1, RateIntervalUnit.MINUTES);
    }

    @GetMapping("/limiter")
    public BaseResult<?> limiter() throws Exception {

        //从此RateLimiter处获取许可，直到获得许可为止。
        //rateLimiter.acquire();

        //仅在调用时可用时才获得许可。
        if (rateLimiter.tryAcquire(1,TimeUnit.SECONDS)) {
            return BaseResultUtil.ok("访问正常");
        }
        return BaseResultUtil.error("你被限流了");
    }


}
