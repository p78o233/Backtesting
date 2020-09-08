package com.example.demo.service.impl;
/*
 * @author p78o2
 * @date 2020/9/3
 */

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.vo.IndexVo;
import com.example.demo.mapper.IndexMapper;
import com.example.demo.service.IndexService;
import com.example.demo.utils.ApiUtils;
import com.example.demo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexMapper mapper;
//    @Value("${testAppKey}")
//    private String key;
//    @Value("${testSign}")
//    private String sign;

    @Override
    public IndexVo getIndexVo(int userId) {
//        获取全部正常的item
        List<GroupItem> groupItems = new ArrayList<>();
        groupItems = mapper.getAllGroupItem(userId);
//        总盈亏百分比
        double profitPercentage = 0.0;
//        总盈亏数字
        double profitValue = 0.0;
//        总市值
        double totalMarketValue = 0.0;
//        总成本
        double totalCost = 0.0;
//        当日总成本
        double dayCost = 0.0;
//        当日总市值
        double dayMarketValue = 0.0;
//        当日盈亏百分比
        double dayProfitPercentage = 0.0;
//        当日盈亏数字
        double dayProfitValue = 0.0;

        for (GroupItem item : groupItems) {
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            long endTime = cal.getTime().getTime() / 1000;
            float nowPrice = ApiUtils.getStockNowPrice(item.getSymbol());
            totalMarketValue += nowPrice * item.getBuyNum();
            totalCost += item.getBuyPrice() * item.getBuyNum();
            if (item.getEndTime() < endTime && item.getEndTime() != 0) {
                dayCost += ApiUtils.getStockYesterdayPrice(item.getSymbol()) * item.getBuyNum();
                dayMarketValue += ApiUtils.getStockNowPrice(item.getSymbol()) * item.getBuyNum();
            }
        }
//        总的
        profitValue = totalMarketValue - totalCost;
        profitPercentage = ((totalMarketValue / totalCost)- 1) * 100;
//        当日的
        dayProfitValue = dayMarketValue - dayCost;
        dayProfitPercentage = ((dayMarketValue / dayCost) - 1) * 100;

        IndexVo vo = new IndexVo(profitPercentage,profitValue,totalMarketValue,totalCost,dayProfitPercentage,dayProfitValue);
        return vo;
    }

}
