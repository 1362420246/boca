package com.qbk.boca.nacos.controller;

import com.qbk.boca.nacos.properties.LocalCacheProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过 ConfigurationProperties 注解实现自动刷新
 */
@RestController
@RequestMapping("/cfg")
public class ConfigPropertiesContoller {

    @Autowired
    private LocalCacheProperties cacheProperties;

    @RequestMapping("/get")
    public Object get() {
        return cacheProperties;
    }
}
