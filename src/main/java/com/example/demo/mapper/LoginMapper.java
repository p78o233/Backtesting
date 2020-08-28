package com.example.demo.mapper;

import com.example.demo.domain.po.TestGroup;
import com.example.demo.domain.po.User;
import org.apache.ibatis.annotations.*;

/*
 * @author p78o2
 * @date 2020/8/28
 */
@Mapper
public interface LoginMapper {
//    登陆sql
    @Select("select count(*) from user where account = #{account} and isdel = 0")
    int isExistAccount(@Param("account")String account);
    @Select("select * from user where account = #{account} and pwd = #{pwd} and isdel = 0")
    int checkAccountPwd(@Param("account")String account,@Param("pwd")String pwd);
    @Update("update user set token = #{token} where id = #{id}")
    int updateToken(@Param("id")int id,@Param("token")String token);

//    注册
    @Insert("insert into user (account,pwd,createTime) values (#{u.account},#{u.pwd},#{u.createTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int userRegister(@Param("u")User user);
    @Insert("insert into testgroup (userId,groupName,createTime) values (#{t.userId},#{t.groupName},#{t.createTime})")
    int insertTestGroup(@Param("t")TestGroup testGroup);
}
