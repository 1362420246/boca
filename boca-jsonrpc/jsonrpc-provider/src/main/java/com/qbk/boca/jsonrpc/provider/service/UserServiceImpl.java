package com.qbk.boca.jsonrpc.provider.service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.qbk.boca.jsonrpc.api.User;
import com.qbk.boca.jsonrpc.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AutoJsonRpcServiceImpl
public class UserServiceImpl implements UserService {

    @Override
    public User createUser(String userName, String password) {
        log.info("参数是:{},{}",userName,password);
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        return user;
    }
}
