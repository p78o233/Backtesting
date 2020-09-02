package com.example.demo.mapper;

import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.Stock;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*
 * @author p78o2
 * @date 2020/9/1
 */
@Mapper
public interface GroupItemMapper {
//    根据组别id获取组别详细列表
    @Select("select * from groupitem where groupId = #{groupId} and isdel = 0")
    List<GroupItem> getGroupAllItems(@Param("groupId")int groupId);
//    获取组别item详细
    @Select("select * from groupitem where id = #{id} and isdel = 0")
    GroupItem getGroupItemDetail(@Param("id")int id);
//    根据股票编号模糊查询
    @Select("select * from stock where symbol like '%${symbol}%' order by id asc limit 0 , 20")
    List<Stock> getStockSearch (@Param("symbol")String symbol);

//    新增修改
    @Insert("insert into groupitem (buyTime,buyNum,buyPrice,symbol,sname,groupId,userId,endTime,createTime) values (#{g.buyTime},#{g.buyNum},#{g.buyPrice},#{g.symbol}," +
            "#{g.sname},#{g.groupId},#{g.userId},#{g.endTime},#{g.createTime})")
    int insertGroupItem(@Param("g")GroupItem groupItem);
    @Update("update groupitem set buyTime = #{g.buyTime} ,buyNum = #{g.buyNum},buyPrice = #{g.buyPrice},modifyTime = #{g.modifyTime} ,endTime =#{g.endTime} where id = #{g.id}")
    int updateGroupItem(@Param("g")GroupItem groupItem);

//    批量操作
    @Update("<script>" +"update groupitem set isdel = 1 "+
            " and id in "+
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>"+
            "#{id}"+
            "</foreach>"+
            "</script>")
    int batchDeleteAllGroupItem(@Param("ids")List<Integer> ids);

    @Select("select symbol from groupitem where id = #{id}")
    String getSymbolByItemId(@Param("id")int id);
    @Select("select sname from stock where symbol = #{symbol}")
    String getSnameSymbol(@Param("symbol")String symbol);

    @Update("update groupitem set buyTime = #{g.buyTime} ,buyPrice = #{g.buyPrice} where id = #{g.id}")
    int batchEditBeginTimeGroupItem(@Param("g")GroupItem groupItem);

    @Update("<script>" +"update groupitem set endTime = #{endTime} "+
            " where id in "+
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>"+
            "#{id}"+
            "</foreach>"+
            "</script>")
    int batchEditEndTimeGroupItem(@Param("endTime") long endTime,@Param("ids")List<Integer> ids);
}
