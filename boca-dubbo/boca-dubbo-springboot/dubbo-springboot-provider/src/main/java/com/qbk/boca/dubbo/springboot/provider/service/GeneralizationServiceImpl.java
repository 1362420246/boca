package com.qbk.boca.dubbo.springboot.provider.service;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * 泛化
 */
@DubboService(protocol = {"dubbo"})
public class GeneralizationServiceImpl implements GeneralizationService {
    @Override
    public String getName(String name) {
        return "Generalization:" + name;
    }
}
