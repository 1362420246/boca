package com.qbk.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 全局降级测试
 */
@DefaultProperties( defaultFallback = "defaultFallback")
@Slf4j
@RestController
public class TestContoller2 {

    /**
     * 未添加HystrixCommand注解不能降级
     */
    @GetMapping("/get2")
    public BaseResult<?> get2(Integer n){
        n =  10 / 0;
        return BaseResultUtil.ok();
    }

    /**
     * 添加HystrixCommand注解才能在异常时候降级
     */
    @GetMapping("/get3")
    @HystrixCommand
    public BaseResult<?> get3(String s){
        int n =  10 / 0;
        return BaseResultUtil.ok();
    }

    @GetMapping("/get4")
    @HystrixCommand
    public BaseResult<?> get4(Double x){
        int n =  10 / 0;
        return BaseResultUtil.ok();
    }

    /**
     * 全局回退 ，不能有参数
     */
    public BaseResult<?> defaultFallback(){
        log.info(Thread.currentThread().getName());
        return BaseResultUtil.error("全局回退");
    }
}
