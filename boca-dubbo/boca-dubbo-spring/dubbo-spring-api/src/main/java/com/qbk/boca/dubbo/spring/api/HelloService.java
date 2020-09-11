package com.qbk.boca.dubbo.spring.api;

import com.qbk.boca.dubbo.spring.api.dto.ParamDTO;

import javax.validation.Valid;

public interface HelloService {

    String sayHello(@Valid ParamDTO dto);
}
