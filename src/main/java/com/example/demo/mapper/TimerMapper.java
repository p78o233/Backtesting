package com.example.demo.mapper;

import com.example.demo.domain.po.Stock;
import com.example.demo.domain.po.StockRecord;
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
            "insert into stock(stoid, symbol, sname) values " +
            "<foreach collection='stockList' item='item' index='index' separator=','>"+
            "(#{item.stoid}, #{item.symbol}, #{item.sname})"+
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
}
