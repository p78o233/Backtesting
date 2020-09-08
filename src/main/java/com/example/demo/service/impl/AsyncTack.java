package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.dto.BatchInsertGroupItemDto;
import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.Stock;
import com.example.demo.mapper.LoginMapper;
import com.example.demo.service.GroupItemService;
import com.example.demo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*
 * @author p78o2
 * @date 2020/9/7
 */
@Component
public class AsyncTack {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private GroupItemService groupItemService;
    @Value("${testAppKey}")
    private String key;
    @Value("${testSign}")
    private String sign;
    //    批量插入耗时任务
    @Async
    public void batchLongTime(int userId,int groupId){
        //            获取全部在注册的股票
        List<Stock> allStock = loginMapper.getAllStock();
        BatchInsertGroupItemDto dto = new BatchInsertGroupItemDto();
        List<String> sys = new ArrayList<>();
        for(Stock stock : allStock){
           sys.add(stock.getSymbol());
        }

        dto.setGroupId(groupId);
        dto.setUserId(userId);
        dto.setSymbol(sys);
        dto.setBuyNum(100);
        dto.setBuyTime(new Date().getTime()/1000);
        dto.setEndTime(0L);
        groupItemService.batchInsertGroupItem(dto);
    }

    //    批量插入耗时任务
    @Async
    public void batchLongTimeStocks(int userId,int groupId,List<Stock> allStock){
        //            获取全部在注册的股票
        BatchInsertGroupItemDto dto = new BatchInsertGroupItemDto();
        List<String> sys = new ArrayList<>();
        for(Stock stock : allStock){
            sys.add(stock.getSymbol());
        }

        dto.setGroupId(groupId);
        dto.setUserId(userId);
        dto.setSymbol(sys);
        dto.setBuyNum(100);
        dto.setBuyTime(new Date().getTime()/1000);
        dto.setEndTime(0L);
        groupItemService.batchInsertGroupItem(dto);
    }

}
