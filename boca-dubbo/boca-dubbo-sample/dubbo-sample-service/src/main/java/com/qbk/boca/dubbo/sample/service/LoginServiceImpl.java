package com.qbk.boca.dubbo.sample.service;

import com.qbk.boca.dubbo.sample.api.ILoginService;

/**
 * dubbo://192.168.99.1:20880/com.qbk.boca.dubbo.sample.api.ILoginService
 */
public class LoginServiceImpl implements ILoginService {

    @Override
    public String login(String username, String password) {
        //写业务逻辑
        if(username.equals("admin")&&password.equals("admin")){
            return "SUCCESS";
        }
        return "FAILED";
    }
}
