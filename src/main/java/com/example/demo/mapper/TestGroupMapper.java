package com.example.demo.mapper;

import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.TestGroup;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/*
 * @author p78o2
 * @date 2020/8/31
 */
@Mapper
public interface TestGroupMapper {
//    获取全部分组
    @Select("select * from testgroup where isdel = 0 and userId = #{userId}")
    List<TestGroup> getAllTestGroup(@Param("userId")int userId);

//    检查自己是否有同名的回测分组
    @Select("select count(*) from testgroup where groupName = #{groupName} and userId = #{userId} and isdel = 0")
    int checkGroupNameExist(@Param("groupName")String groupName,@Param("userId")int userId);

    @Select("select count(*) from testgroup where groupName = #{groupName} and userId = #{userId} and isdel = 0 and id != #{id}")
    int checkGroupNameExistEdit(@Param("groupName")String groupName, @Param("userId")int userId, @Param("id")int id);

    @Insert("insert into testgroup (userId,groupName,createTime) values (#{t.userId},#{t.groupName},#{t.createTime})")
    int insertTestGroup(@Param("t")TestGroup testGroup);

    @Update("update testgroup set groupName = #{t.groupName} , modifyTime = #{t.modifyTime} where id = #{t.id}")
    int updateTestGroup(@Param("t")TestGroup testGroup);

//    批量删除回测分组
    @Update("<script>" +"update testgroup set isdel = 1 and userId = #{userId}"+
            " and id in "+
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>"+
            "#{id}"+
            "</foreach>"+
            "</script>")
    int batchDeleteTestGroup(@Param("userId")int userId,@Param("ids")List<Integer> ids);
    @Update("<script>" +"update groupitem set isdel = 1 and userId = #{userId}"+
            " and groupId in "+
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>"+
            "#{id}"+
            "</foreach>"+
            "</script>")
    int batchDeleteAllGroupItem(@Param("userId")int userId,@Param("ids")List<Integer> ids);
    @Select("select isdefault from testgroup where isdel = 0 and id = #{id}")
    int getIsDefault(@Param("id")int id);

    @Select("select * from groupitem where groupId = #{groupId} and isdel = 0")
    List<GroupItem> groupItems (@Param("groupId")int groupId);
}
