package com.example.demo.mapper;

import com.example.demo.domain.po.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
 * @author p78o2
 * @date 2020/8/28
 */
@Mapper
public interface TestMapper {
    @Select("select * from test")
    List<Test> getTests();
}
