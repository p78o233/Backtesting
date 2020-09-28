package com.example.demo.mapper;

import com.example.demo.domain.po.Stock;
import com.example.demo.domain.po.StockRecord;
import com.example.demo.domain.po.TestGroup;
import com.example.demo.domain.po.User;
import com.example.demo.domain.vo.GroupItemVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*
 * @author p78o2
 * @date 2020/8/31
 */
@Mapper
public interface TimerMapper {
//    批量删除股票列表
    @Delete("delete from stock")
    int deleteStockList();
//    批量插入股票列表
    @Insert({
            "<script>",
            "insert ignore into stock(stoid, symbol, sname,createTime,modifyTime) values " +
            "<foreach collection='stockList' item='item' index='index' separator=','>"+
            "(#{item.stoid}, #{item.symbol}, #{item.sname},now(),now())"+
            "</foreach>"+
            "</script>"
    })
    int insertStock(@Param(value = "stockList") List<Stock> stockList);


//    获取全部股票列表
    @Select("select * from stock")
    List<Stock> getAllStock();
//    批量新增股票记录
    @Insert({
            "<script>",
            "insert into stockrecord (symbol, sname,beginPrice,endPrice,highPrice,lowPrice,recordTime) values " +
                    "<foreach collection='stockRecordList' item='item' index='index' separator=','>"+
                    "(#{item.symbol}, #{item.sname}, #{item.beginPrice}, #{item.endPrice}, #{item.highPrice}, #{item.lowPrice}, #{item.recordTime})"+
                    "</foreach>"+
                    "</script>"
    })
    int insertStockRecord(@Param("stockRecordList")List<StockRecord> stockRecords);

//    获取全部人的默认分组的id
    @Select("select id from user where isdel = 0")
    List<Integer> getAllUserId();
    @Select("select id from testgroup where userId = #{userId} and isdefault = 1")
    int getGroupIdByUserId(@Param("userId")int userId);
    @Select("select * from stock where symbol not in (select symbol from groupitem where groupId = #{groupId} and userId = #{userId})")
    List<Stock> getNoneExitStock(@Param("groupId")int groupId,@Param("userId")int userId);

//    获取全部用户的默认分组的id
    @Select("select id from testgroup where isdel = 0 and isdefault = 1 and userId in (select id from user where isdel = 0)")
    List<Integer> getGroupIds();
//    批量插入缓存
    @Insert({
            "<script>",
            "insert into defaultitem (id,buyTime,buyNum,buyPrice,symbol,sname,groupId,isdel,createTime,modifyTime,userId,endTime,nowPrice,profit,profitPencent,profitPencentNum,totalDays,raiseDays,dropDays,blanceDays) values ",
            "<foreach collection='testLists' item='item' index='index' separator=','>",
            "(#{item.id}, #{item.buyTime},#{item.buyNum}, #{item.buyPrice},#{item.symbol}, #{item.sname}, #{item.groupId},#{item.isdel}, #{item.createTime}, #{item.modifyTime},#{item.userId}" +
                    ", #{item.endTime}, #{item.nowPrice},#{item.profit}" +
                    ", #{item.profitPencent}, #{item.profitPencentNum},#{item.totalDays}" +
                    ", #{item.raiseDays}, #{item.dropDays},#{item.blanceDays})",
            "</foreach>",
            "</script>"
    })
    int insertDefaultItem(@Param("testLists") List<GroupItemVo> groupItemVos );

    @Delete("delete from defaultitem")
    int deleteDefaultItem();

//    删除所有的基金信息
    @Delete("delete from defaultitem where symbol like 'sh5%' or symbol like 'sz18' or symbol like 'sz15' or symbol like 'sz16'")
    int deleteSymbolDefaultItem();
    @Delete("delete from groupitem where symbol like 'sh5%' or symbol like 'sz18' or symbol like 'sz15' or symbol like 'sz16'")
    int deleteSymbolGroupItem();
    @Delete("delete from stock where symbol like 'sh5%' or symbol like 'sz18' or symbol like 'sz15' or symbol like 'sz16'")
    int deleteSymbolStock();
    @Delete("delete from stockrecord where symbol like 'sh5%' or symbol like 'sz18' or symbol like 'sz15' or symbol like 'sz16'")
    int deleteSymbolStockRecord();
}
