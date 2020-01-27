package com.qbk.boca.demo.controller;

import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author ：quboka
 * @description：
 * @date ：2020/1/2 15:16
 */
@RestController
public class TestController {

    @GetMapping("/")
    public BaseResult<?> test() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return BaseResultUtil.ok("s"+ System.currentTimeMillis());
    }

    @GetMapping("/get")
    public BaseResult<?> get() throws InterruptedException {
        return BaseResultUtil.ok("GET"+ System.currentTimeMillis());
    }
}
