package com.qbk.seata.at.b.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TestBMapper {

    @Update("update b set number = number -1  where id = 1")
    int update();
}
