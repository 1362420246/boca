package com.qbk.seata.at.b.service;

import com.qbk.seata.at.b.mapper.TestBMapper;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestBService {

    @Autowired
    private TestBMapper testBMapper;

    @Transactional(rollbackFor = Exception.class)
    public void update(){
        System.out.println("当前 XID:" + RootContext.getXID());
        testBMapper.update();
    }

}
