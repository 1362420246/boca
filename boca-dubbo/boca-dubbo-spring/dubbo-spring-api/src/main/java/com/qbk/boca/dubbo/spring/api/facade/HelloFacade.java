package com.qbk.boca.dubbo.spring.api.facade;

import com.qbk.boca.dubbo.spring.api.dto.ParamDTO;

import javax.validation.Valid;

public interface HelloFacade {

    String sayHello(@Valid ParamDTO dto);
}
