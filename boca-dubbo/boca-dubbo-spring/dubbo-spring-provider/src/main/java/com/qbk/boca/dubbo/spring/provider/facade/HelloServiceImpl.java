package com.qbk.boca.dubbo.spring.provider.facade;

import com.qbk.boca.dubbo.spring.api.facade.HelloFacade;
import com.qbk.boca.dubbo.spring.api.dto.ParamDTO;
import com.qbk.boca.dubbo.spring.provider.service.LocalService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

@Service(validation = "true")
public class HelloServiceImpl implements HelloFacade {

    @Autowired
    private LocalService localService;

    @Override
    public String sayHello(@Valid ParamDTO dto) {
        String str = localService.get(dto.getName());
        return "Hello :" + str ;
    }
}
