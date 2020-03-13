package com.qbk.boca.demo.hystrix;

import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import com.qbk.boca.bean.entity.User;
import com.qbk.boca.demo.api.UserApi;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 统一熔断
 * 道熔断的异常信息
 */
@Slf4j
@Component
public class HystrixClientFallbackFactory implements FallbackFactory<UserApi> {
    @Override
    public UserApi create(Throwable throwable) {
        log.error("打印异常：" + throwable);
        return new UserApi() {
            @Override
            public BaseResult<User> getUser(String username ,String password) {
                return BaseResultUtil.error("发生错误:" + throwable.getMessage());
            }
            @Override
            public BaseResult<User> getApiUser(Map<String, String> headerMap) {
                return BaseResultUtil.error("发生错误:" + throwable.getMessage());
            }
        };
    }
}
