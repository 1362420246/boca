package com.qbk.boca.demo.api;

import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import com.qbk.boca.bean.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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

    /**
     * get请求头添加参数
     */
    @GetMapping(value = "/get/test" , headers = {"test1={test1}","test2={test2}"})
    BaseResult<?> getTest(@RequestParam("name") String name, @RequestParam("test1") String test1, @RequestParam("test2") String test2);

    /**
     * post请求头添加参数
     */
    @PostMapping(value = "/post/test" , headers = {"test1={qbk}","test2={quboka}"})
    BaseResult<String> postTest(@RequestBody Map<String,Object> map, @RequestParam("qbk") String qbk, @RequestParam("quboka") String quboka);

    @Component
    class UserClientImpl implements UserClient{
        @Override
        public ResponseEntity<BaseResult<User>> getUser() {
            return new ResponseEntity<>(BaseResultUtil.error("网路异常"), HttpStatus.OK);
        }

        @Override
        public BaseResult<?> getTest(String name, String test1, String test2) {
            return BaseResultUtil.error("网路异常");
        }

        @Override
        public BaseResult<String> postTest(Map<String, Object> map, String qbk, String quboka) {
            return BaseResultUtil.error("网路异常");
        }
    }

}
