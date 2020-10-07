package com.qbk.seata.at.a.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        //服务名
        name = "seata-at-b"
)
public interface SeataAtBdemoApi {

    @GetMapping("/update")
    String update();
}
