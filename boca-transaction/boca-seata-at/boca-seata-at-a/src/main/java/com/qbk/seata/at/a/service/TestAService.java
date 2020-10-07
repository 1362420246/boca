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

    @Autowired
    private TestAMapper testAMapper;

    @Autowired
    private SeataAtBdemoApi seataAtBdemoApi;

    /**
     * io.seata.spring.annotation.GlobalTransactional 全局事务
     */
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void update(){
        System.out.println("当前 XID:" + RootContext.getXID());
        testAMapper.update();
        seataAtBdemoApi.update();
        int a = 10/0;
    }

}
