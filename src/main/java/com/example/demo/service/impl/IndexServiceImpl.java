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
    @Value("${testAppKey}")
    private String key;
    @Value("${testSign}")
    private String sign;

    @Override
    public IndexVo getIndexVo(int userId) {
//        获取全部正常的item
        List<GroupItem> groupItems = new ArrayList<>();
        groupItems = mapper.getAllGroupItem(userId);
//        总盈亏百分比
        double profitPercentage = 0.0f;
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
            float nowPrice = getStockNowPrice(item.getSymbol());
            totalMarketValue += nowPrice * item.getBuyNum();
            totalCost += item.getBuyPrice() * item.getBuyNum();
            if (item.getEndTime() < endTime && item.getEndTime() != 0) {
                dayCost += getStockYesterdayPrice(item.getSymbol()) * item.getBuyNum();
                dayMarketValue += getStockNowPrice(item.getSymbol()) * item.getBuyNum();
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

    //    第三方接口查询单个查询实时价格
    public float getStockNowPrice(String stockNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("app", "finance.stock_realtime");
        params.put("symbol", stockNum);
        params.put("appkey", key);
        params.put("sign", sign);
        String httpStr = HttpUtils.get("http://api.k780.com", params);
        JSONObject httpObj = JSONObject.parseObject(httpStr);
        if (httpObj.getString("success").equals("0")) {
            return 0.0f;
        }
        JSONObject resultObj = httpObj.getJSONObject("result");
        JSONObject lists = resultObj.getJSONObject("lists");
        JSONObject obj = lists.getJSONObject(stockNum);
        return obj.getFloat("last_price");
    }

    //    第三方接口查询昨日收盘数据
    public float getStockYesterdayPrice(String stockNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("app", "finance.stock_realtime");
        params.put("symbol", stockNum);
        params.put("appkey", key);
        params.put("sign", sign);
        String httpStr = HttpUtils.get("http://api.k780.com", params);
        JSONObject httpObj = JSONObject.parseObject(httpStr);
        if (httpObj.getString("success").equals("0")) {
            return 0.0f;
        }
        JSONObject resultObj = httpObj.getJSONObject("result");
        JSONObject lists = resultObj.getJSONObject("lists");
        JSONObject obj = lists.getJSONObject(stockNum);
        return obj.getFloat("yesy_price");
    }
}
