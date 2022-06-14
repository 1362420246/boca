package com.qbk.boca.jsonrpc.consumer.controller;

import com.qbk.boca.jsonrpc.api.User;
import com.qbk.boca.jsonrpc.api.UserService;
import com.qbk.boca.jsonrpc.consumer.discovery.RpcServerDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    /**
     * 单体服务测试
     */
    @GetMapping("/test")
    public User test(){
        //rpc调用
        return userService.createUser("client","123456");
    }

    @Autowired
    private RpcServerDiscovery  rpcServerDiscovery;

    /**
     * 集群测试
     */
    @GetMapping("/cluster")
    public User cluster(){
        //通过注册中心获取rpc服务
        UserService rpcServer = rpcServerDiscovery.getRpcServer("jsonrpc-provider", UserService.class);
        //rpc调用
        return rpcServer.createUser("cluster","123456");
    }
}
