package com.qbk.boca.dubbo.spring.provider.service;

import org.springframework.stereotype.Service;

@Service
public class LocalService {

    public String get(String param){
        return "local:"+param;
    }
}
