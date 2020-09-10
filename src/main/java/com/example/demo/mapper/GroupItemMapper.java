package com.example.demo.mapper;

import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.Stock;
import com.example.demo.domain.po.StockRecord;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/*
 * @author p78o2
 * @date 2020/9/1
 */
@Mapper
public interface GroupItemMapper {
//    根据组别id获取组别详细列表
    @Select("select count(*) from groupitem where groupId = #{groupId} and isdel = 0")
    int getGroupAllItemCount(@Param("groupId")int groupId);
    @Select("select * from groupitem where groupId = #{groupId} and isdel = 0 order by modifyTime desc limit #{start} , #{pageSize}")
    List<GroupItem> getGroupAllItems(@Param("groupId")int groupId,@Param("start")int start,@Param("pageSize")int pageSize);
//    获取组别item详细
    @Select("select * from groupitem where id = #{id} and isdel = 0")
    GroupItem getGroupItemDetail(@Param("id")int id);
//    根据股票编号模糊查询
    @Select("select * from stock where symbol like '%${symbol}%' order by id asc limit 0 , 20")
    List<Stock> getStockSearch (@Param("symbol")String symbol);

//    新增修改
    @Insert("insert into groupitem (buyTime,buyNum,buyPrice,symbol,sname,groupId,userId,endTime,createTime,modifyTime) values (#{g.buyTime},#{g.buyNum},#{g.buyPrice},#{g.symbol}," +
            "#{g.sname},#{g.groupId},#{g.userId},#{g.endTime},#{g.createTime},#{g.modifyTime})")
    int insertGroupItem(@Param("g")GroupItem groupItem);
    @Update("update groupitem set buyTime = #{g.buyTime} ,buyNum = #{g.buyNum},buyPrice = #{g.buyPrice},modifyTime = #{g.modifyTime} ,endTime =#{g.endTime} where id = #{g.id}")
    int updateGroupItem(@Param("g")GroupItem groupItem);
    @Insert({
            "<script>",
            "insert into groupitem (buyTime,buyNum,buyPrice,symbol,sname,groupId,userId,endTime,createTime,modifyTime) values ",
            "<foreach collection='testLists' item='item' index='index' separator=','>",
            "(#{item.buyTime}, #{item.buyNum}, #{item.buyPrice},#{item.symbol}, #{item.sname}, #{item.groupId},#{item.userId}, #{item.endTime}, #{item.createTime},#{item.modifyTime})",
            "</foreach>",
            "</script>"
    })
    int bacthGroupItem(@Param(value = "testLists")List<GroupItem> testLists);
//    批量操作
    @Update("<script>" +"update groupitem set isdel = 1 "+
            " and id in "+
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>"+
            "#{id}"+
            "</foreach>"+
            "</script>")
    int batchDeleteAllGroupItem(@Param("ids")List<Integer> ids);
    @Select("select isdefault from testgroup where isdel = 0 and id = #{id}")
    int getIsDefault(@Param("id")int id);

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

//    检查买入时间是否大于卖出时间
    @Select("select count(*) from groupitem where endTime < #{buyTime} and endTime != 0 and isdel = 0 and id = #{id}")
    int countBuyBiggerEnd(@Param("buyTime")long buyTime,@Param("id")int id);
//    检查卖出时间是否小于买入时间
    @Select("select count(*) from groupitem where #{endTime} < buyTime and isdel = 0 and id = #{id}")
    int countEndSmallerBuy(@Param("endTime")long endTime,@Param("id")int id);

    @Select("select createTime from stock where symbol = (select symbol from groupitem where id = #{id})")
    Date getStockBySymbol(@Param("id")int id);

    @Select("select * from stockrecord where symbol = #{symbol} and recordTime > #{beginTime} and recordTime < #{endTime}")
    List<StockRecord> getAllStockEndTime(@Param("symbol")String symbol,@Param("beginTime")Long beginTime,@Param("endTime")Long endTime);

    @Select("select * from stockrecord where symbol = #{symbol} and recordTime > #{beginTime}")
    List<StockRecord> getAllStockEndTimeNone(@Param("symbol")String symbol,@Param("beginTime")Long beginTime);

    @Select("select endPrice from stockrecord where symbol = #{symbol} and recordTime = #{recordTime}")
    Float getEndPrice(@Param("symbol")String symbol,@Param("recordTime") Long recordTime);
}
