package com.qbk.boca.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：quboka
 * @description：
 * @date ：2020/1/2 15:16
 */
@RestController
public class TestController {

    @GetMapping("/")
    public String get(){
        return "s";
    }
}
