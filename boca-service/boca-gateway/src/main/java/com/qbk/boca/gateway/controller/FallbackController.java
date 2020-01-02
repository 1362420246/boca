package com.qbk.boca.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public Map<String,Object> fallback(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status",404);
        map.put("message","服务不存在！");
        return map;
    }

}
