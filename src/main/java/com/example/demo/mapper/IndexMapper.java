package com.example.demo.mapper;

import com.example.demo.domain.po.GroupItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
 * @author p78o2
 * @date 2020/9/3
 */
@Mapper
public interface IndexMapper {
    @Select("select * from groupitem where userId = #{userId} and isdel = 0 and groupId not in (select id from testgroup where userId = #{userId} and isdel = 0 and isdefault = 1)")
    List<GroupItem> getAllGroupItem(@Param("userId")int userId);
}
