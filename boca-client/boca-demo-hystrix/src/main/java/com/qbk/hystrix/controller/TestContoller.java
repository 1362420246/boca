package com.qbk.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 测试熔断与降级
 */
@Slf4j
@RestController
public class TestContoller {

    /**
     * 异常回退方法,要与原方法参数一致
     */
    public BaseResult<?> errorFallback(String s){
        log.info(Thread.currentThread().getName());
        return BaseResultUtil.error("异常回退方法");
    }

    /**
     * 降级测试
     */
    @GetMapping("/get1")
    @HystrixCommand(
            fallbackMethod = "errorFallback" ,
            //参考 HystrixCommandProperties
            commandProperties = {
                    //超时时间
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "6000"),
                    //线程隔离策略 ， 信号量:在调用线程上执行{@link HystrixCommand#run()}方法
                    @HystrixProperty(name="execution.isolation.strategy",value = "SEMAPHORE")
            }
    )
    public BaseResult<?> get1(String s){
        log.info(Thread.currentThread().getName());
        int n =  10 / 0;
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return BaseResultUtil.ok();
    }
    //--------------------------------------熔断、降级分割线-------------------------------------------------------

    /**
     * 熔断测试
     */
    @GetMapping("/get5")
    @HystrixCommand(
            fallbackMethod = "errorFallback" ,
            //参考 HystrixCommandProperties
            commandProperties = {
                    //开启断路器
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    //请求量阈值，默认为20
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    //过多长时间，熔断器再次检测是否开启，默认为5000，即5s钟
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    //错误率，默认50%
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
            }
    )
    public BaseResult<?> get5(String s){
        if (Objects.isNull(s)){
            throw new RuntimeException("错误");
        }
        return BaseResultUtil.ok();
    }

}
