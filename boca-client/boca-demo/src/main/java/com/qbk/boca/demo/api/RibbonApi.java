package com.qbk.boca.demo.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 测试负载均衡
 */
@FeignClient(
        //服务名
        name = "${boca.producer.name}"
)
public interface RibbonApi{

    /**
     * 获取生产者服务端口
     */
    @GetMapping("/ribbon/port")
    String getPort();
}
