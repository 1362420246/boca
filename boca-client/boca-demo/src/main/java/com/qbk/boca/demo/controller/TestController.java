package com.qbk.boca.demo.controller;

import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import com.qbk.boca.bean.RespMap;
import com.qbk.boca.core.exception.BasicException;
import com.qbk.boca.core.util.SnowFlakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author ：quboka
 * @description： 提供给网关测试
 * @date ：2020/1/2 15:16
 */
@RestController
public class TestController {

    @Autowired
    private SnowFlakeGenerator snowFlakeGenerator ;

    @GetMapping("/test")
    public RespMap test() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return RespMap.build("test:" + snowFlakeGenerator.nextId());
    }

    @GetMapping("/get")
    public BaseResult<?> get() {
        return BaseResultUtil.ok("GET:"+ snowFlakeGenerator.nextId());
    }

    @GetMapping("/qzk")
    public BaseResult<?> error() {
        if(snowFlakeGenerator.nextId() %2 == 0){
            throw new BasicException("测试统一异常拦截");
        }
        return BaseResultUtil.ok("error:"+ snowFlakeGenerator.nextId());
    }
}
