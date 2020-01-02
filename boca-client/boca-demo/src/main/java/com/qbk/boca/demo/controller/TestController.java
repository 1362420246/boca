package com.qbk.boca.demo.controller;

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
    public String test() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return "s";
    }

    @GetMapping("/get")
    public String get() throws InterruptedException {
        return "get";
    }
}
