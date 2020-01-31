package com.qbk.boca.demo.api;

import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.entity.User;
import com.qbk.boca.demo.config.FeignConfiguration;
import com.qbk.boca.demo.hystrix.HystrixClientFallbackFactory;
import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

@FeignClient(
        //服务名
        name = "${boca.producer.name}" ,
        //uri 路径
        path = "${boca.producer.path}" ,
        // 统一熔断
        fallbackFactory = HystrixClientFallbackFactory.class ,
        //配置类
        configuration = FeignConfiguration.class ,
        //区分相同feign客户端时 的id
        contextId = "userApi"
)
public interface UserApi {

    /**
     * feign 注解 ：https://github.com/OpenFeign/feign#dynamic-query-parameters
     * 添加请求头 @Headers("Content-Type: application/json")
     */
    @Headers("username: {username} ")
    @RequestLine("GET /get/user")
    BaseResult<User> getUser(@Param("username")String username );

    /**
     * 多个请求头参数
     */
    @RequestLine("GET /get/user")
    BaseResult<User> getApiUser(@HeaderMap Map<String, String> headerMap );
}
