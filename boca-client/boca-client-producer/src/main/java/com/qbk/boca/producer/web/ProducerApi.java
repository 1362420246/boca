package com.qbk.boca.producer.web;

import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import com.qbk.boca.bean.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

/**
 * 生产者测试接口
 * 提供demo服务feign调用
 */
@RequestMapping("/producer")
@RestController
public class ProducerApi {

    @GetMapping("/get/user")
    public BaseResult<User> getUser(HttpServletRequest request,HttpServletResponse response){
        //获取请求头中参数
        final String username = request.getHeader("username");
        final String password = request.getHeader("password");
        System.out.println(username);
        System.out.println(password);

        //添加响应头
        response.addHeader("token","2333333333333");

        return BaseResultUtil.ok("获取用户成功",
                User.builder()
                        .id(1)
                        .username("qbk")
                        .password("123")
                        .salt("ss")
                        .isLock(false)
                        .isDel(false)
                        .build());
    }

    @GetMapping(value = "/get/test")
    BaseResult<?> getTest(String name, HttpServletRequest request , HttpServletResponse response){
        System.out.println("get请求头测试：" + name);
        System.out.println("get请求头测试：" +request.getHeader("test1"));
        System.out.println("get请求头测试：" +request.getHeader("test2"));
        return BaseResultUtil.ok("我是返回数据");
    }


    @PostMapping("/post/test")
    BaseResult<?> postTest(@RequestBody Map<String ,Object> map , HttpServletRequest request , HttpServletResponse response){
        System.out.println("post请求头测试：" + map);
        System.out.println("post请求头测试：" + request.getHeader("test1"));
        System.out.println("post请求头测试：" + request.getHeader("test2"));
        return BaseResultUtil.ok("我是返回数据");
    }
}
