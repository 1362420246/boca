package com.qbk.boca.dubbo.springboot.consumer.service;

import com.qbk.boca.dubbo.springboot.api.HelloService;
import com.qbk.boca.dubbo.springboot.api.QRespon;

/**
 * 服务降级
 */
public class MockServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "触发服务降级";
    }

    @Override
    public QRespon sayKryo(String name) {
        QRespon respon = new QRespon();
        respon.setData("触发服务降级");
        return respon;
    }
}
