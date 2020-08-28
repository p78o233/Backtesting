package com.example.demo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterMapper {
    @Select("select count(*) from user where token = #{token}")
    int checkTokenExist(@Param("token") String token);
}
