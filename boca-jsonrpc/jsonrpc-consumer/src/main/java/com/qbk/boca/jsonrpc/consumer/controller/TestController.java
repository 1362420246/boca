package com.qbk.boca.jsonrpc.consumer.controller;

import com.qbk.boca.jsonrpc.api.User;
import com.qbk.boca.jsonrpc.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public User test(){
        //rpc调用
        return userService.createUser("client","123456");
    }
}
