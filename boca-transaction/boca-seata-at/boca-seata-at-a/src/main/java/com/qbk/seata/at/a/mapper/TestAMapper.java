package com.qbk.seata.at.a.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TestAMapper {

    @Update("update a set number = number +1  where id = 1")
    int update();
}
