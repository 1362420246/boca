package com.qbk.boca.demo.api;

import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import com.qbk.boca.bean.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        //服务名
        name = "${boca.producer.name}" ,
        //uri 路径
        path = "${boca.producer.path}" ,
        //熔断
        fallback = UserClient.UserClientImpl.class
)
public interface UserClient {

    /**
     * @return BaseResult 需要存在无参构造方法
     * ResponseEntity 可以获取响应头
     */
    @GetMapping("/get/user")
    ResponseEntity<BaseResult<User>> getUser();

    @Component
    class UserClientImpl implements UserClient{
        @Override
        public ResponseEntity<BaseResult<User>> getUser() {
            return new ResponseEntity<>(BaseResultUtil.error("网路异常"), HttpStatus.OK);
        }
    }

}
