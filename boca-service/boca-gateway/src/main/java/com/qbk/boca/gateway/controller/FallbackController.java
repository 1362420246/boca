package com.qbk.boca.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FallbackController {

    /**
     * 熔断
     */
    @RequestMapping("/fallback")
    public Map<String,Object> fallback(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code",404);
        map.put("message","网络连接超时！");
        return map;
    }

}
