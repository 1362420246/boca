package com.qbk.boca.demo.controller;

import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.entity.User;
import com.qbk.boca.demo.api.UserApi;
import com.qbk.boca.demo.api.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * feign测试
 */
@RestController
@RequestMapping("/feign")
public class FeignTestController {

    @Autowired
    private UserClient client;

    @Autowired
    private UserApi userApi;

    @GetMapping("/getUser")
    public BaseResult<User> getUser(HttpServletRequest request ,HttpServletResponse response){
        //添加request域对象参数 通过feign拦截器放入请求头
        request.setAttribute("password","123456");

        //feign请求
        final ResponseEntity<BaseResult<User>> resultResponse = client.getUser();

        //获取feign请求中响应头中的数据
        String token = resultResponse.getHeaders().getFirst("token");
        System.out.println(token);

        //响应头添加参数测试
        response.addHeader("token","token test:" + token );

        return resultResponse.getBody();
    }

    @GetMapping("/get/user")
    public BaseResult<User> getApiUser(){
        return userApi.getUser("qbk");
    }

    @GetMapping("/get/api/user")
    public BaseResult<User> getUserApi(){
        return userApi.getApiUser(new HashMap<String,String>(){
            {
                put("username","qinyu");
                put("password","321");
            }
        });
    }

}
