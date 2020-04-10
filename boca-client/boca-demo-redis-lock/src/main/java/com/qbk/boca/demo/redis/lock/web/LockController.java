package com.qbk.boca.demo.redis.lock.web;

import com.alibaba.fastjson.JSONObject;
import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import com.qbk.boca.bean.entity.User;
import com.qbk.boca.core.exception.BasicException;
import com.qbk.boca.core.util.RedisUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁
 */
@RestController
public class LockController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 分布式锁
     */
    @GetMapping("/syn")
    public BaseResult<?> syn(){
        RLock rLock = redissonClient.getLock("syn");
        if(rLock.tryLock()){
            try {
                TimeUnit.SECONDS.sleep(50);
                return BaseResultUtil.ok();
            } catch (Exception e) {
                e.printStackTrace();
                return BaseResultUtil.error();
            }finally {
                rLock.unlock();
            }
        }else {
            return BaseResultUtil.error("同步中，稍后再试");
        }
    }

    @GetMapping("/set")
    public BaseResult<?> set(){
        User user = User.builder().id(1).username("qbk").build();
        final boolean set = redisUtil.set("user:" + user.getUsername(), user, 20);
        return BaseResultUtil.ok(set);
    }

    @GetMapping("/get")
    public BaseResult<?> get(){
        final JSONObject jsonObject = redisUtil.get("user:qbk");
        Optional.ofNullable(jsonObject).orElseThrow( () -> new BasicException("查询为空"));
        final User user = jsonObject.toJavaObject(User.class);
        return BaseResultUtil.ok(user);
    }
}
