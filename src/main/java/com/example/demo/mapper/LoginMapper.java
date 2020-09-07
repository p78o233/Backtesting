package com.example.demo.mapper;

import com.example.demo.domain.po.Stock;
import com.example.demo.domain.po.TestGroup;
import com.example.demo.domain.po.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

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
    User checkAccountPwd(@Param("account")String account,@Param("pwd")String pwd);
    @Update("update user set token = #{token} where id = #{id}")
    int updateToken(@Param("id")int id,@Param("token")String token);

//    检查账号是否重复
    @Select("select count(*) from user where account = #{account} and isdel = 0")
    int checkRegister(@Param("account")String account);

//    注册
    @Insert("insert into user (account,pwd,createTime) values (#{u.account},#{u.pwd},#{u.createTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void userRegister(@Param("u")User user);
    @Insert("insert into testgroup (userId,groupName,createTime,isdefault) values (#{t.userId},#{t.groupName},#{t.createTime},#{t.isdefault})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insertTestGroup(@Param("t")TestGroup testGroup);

    @Select("select * from stock")
    List<Stock> getAllStock();
}
