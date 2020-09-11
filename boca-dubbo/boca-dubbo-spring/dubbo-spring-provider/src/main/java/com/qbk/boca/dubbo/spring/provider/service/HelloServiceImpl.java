package com.qbk.boca.dubbo.spring.provider.service;

import com.qbk.boca.dubbo.spring.api.HelloService;
import com.qbk.boca.dubbo.spring.api.dto.ParamDTO;
import org.apache.dubbo.config.annotation.Service;

import javax.validation.Valid;

@Service(validation = "true")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(@Valid ParamDTO dto) {
        return "Hello "+dto.getName()+" !";
    }
}
