package com.qbk.seata.at.a.controller;

import com.qbk.seata.at.a.service.TestAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestAService testAService;

    @GetMapping("/")
    public String get(){
        try {
            testAService.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "s";
    }
}
