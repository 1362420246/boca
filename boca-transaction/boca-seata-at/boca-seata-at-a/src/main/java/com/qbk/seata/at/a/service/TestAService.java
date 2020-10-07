package com.qbk.seata.at.a.service;

import com.qbk.seata.at.a.fegin.SeataAtBdemoApi;
import com.qbk.seata.at.a.mapper.TestAMapper;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestAService {

    /**
     * 本地 mapper调用
     */
    @Autowired
    private TestAMapper testAMapper;

    /**
     * feign 调用
     */
    @Autowired
    private SeataAtBdemoApi seataAtBdemoApi;

    /**
     * io.seata.spring.annotation.GlobalTransactional 全局事务
     */
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void update(){
        System.out.println("当前 XID:" + RootContext.getXID());
        //本地 mapper调用
        testAMapper.update();
        //feign 调用
        seataAtBdemoApi.update();
        //回滚异常
        int a = 10/0;
    }
}
