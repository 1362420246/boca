package com.qbk.boca.demo.redis.lock.web;

import com.alibaba.fastjson.JSONObject;
import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import com.qbk.boca.bean.entity.User;
import com.qbk.boca.core.exception.BasicException;
import com.qbk.boca.core.util.RedisUtil;
import com.qbk.boca.demo.redis.lock.annotation.DistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Redisson 分布式锁
 */
@RestController
public class LockController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * Redisson 分布式锁
     */
    @GetMapping("/syn")
    public BaseResult<?> syn() throws InterruptedException {
        //获取锁
        RLock rLock = redissonClient.getLock("syn");
        // 尝试加锁，最多等待3秒，上锁以后100秒自动解锁
        if(rLock.tryLock(3, 100, TimeUnit.SECONDS)){
            try {
                TimeUnit.SECONDS.sleep(50);
                return BaseResultUtil.ok();
            } catch (Exception e) {
                e.printStackTrace();
                return BaseResultUtil.error();
            }finally {
                //释放锁
                rLock.unlock();
            }
        }else {
            return BaseResultUtil.error("同步中，稍后再试");
        }
    }


    /**
     * Redisson 注解 分布式锁
     */
    @GetMapping("/lock")
    @DistributedLock(key = "qbklocl")
    public BaseResult<?> lock(@RequestParam(value = "second",defaultValue = "20") Integer second)
            throws InterruptedException {
        TimeUnit.SECONDS.sleep(second);
        return BaseResultUtil.ok();
    }

    /**
     * RedisTemplate 测试
     */
    @GetMapping("/set")
    public BaseResult<?> set(){
        User user = User.builder().id(1).username("qbk").build();
        final boolean set = redisUtil.set("user:" + user.getUsername(), user, 20);
        return BaseResultUtil.ok(set);
    }

    /**
     * RedisTemplate 测试
     */
    @GetMapping("/get")
    public BaseResult<?> get(){
        final JSONObject jsonObject = redisUtil.get("user:qbk");
        Optional.ofNullable(jsonObject).orElseThrow( () -> new BasicException("查询为空"));
        final User user = jsonObject.toJavaObject(User.class);
        return BaseResultUtil.ok(user);
    }
}
