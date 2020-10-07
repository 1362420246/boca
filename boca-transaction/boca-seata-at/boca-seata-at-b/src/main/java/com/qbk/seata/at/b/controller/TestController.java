package com.qbk.seata.at.b.controller;

import com.qbk.seata.at.b.service.TestBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestBService testBService;

    @GetMapping("/update")
    public String update(){
        testBService.update();
        return "成功";
    }
}
