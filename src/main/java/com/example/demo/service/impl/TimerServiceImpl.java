package com.example.demo.service.impl;
/*
 * @author p78o2
 * @date 2020/8/31
 */

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.po.Stock;
import com.example.demo.domain.po.StockRecord;
import com.example.demo.mapper.TimerMapper;
import com.example.demo.service.TimerService;
import com.example.demo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TimerServiceImpl implements TimerService {
    @Autowired
    private TimerMapper timerMapper;
    @Value("${testAppKey}")
    private String key;
    @Value("${testSign}")
    private String sign;

    @Override
    public void insertStock() {
        HashMap<String, String> params = new HashMap<>();
        params.put("app", "finance.stock_list");
        params.put("category", "hs");
        params.put("appkey", key);
        params.put("sign", sign);
        String result = HttpUtils.get("http://api.k780.com", params);
        JSONObject obj = JSONObject.parseObject(result);
        String resultStr = obj.getString("result");
        JSONObject objRestult = JSONObject.parseObject(resultStr);
        List<Stock> stocks = new ArrayList<>();
        stocks = JSONObject.parseArray(objRestult.getString("lists"), Stock.class);
//        删除旧数据
        int deleteStock = timerMapper.deleteStockList();
//        拉取新数据
        int resultInsert = timerMapper.insertStock(stocks);
    }

    @Override
    public void getDailyRecord() {
//        获取stock表的全部内容
        List<Stock> allStocks = new ArrayList<>();
        allStocks = timerMapper.getAllStock();
        int last = 0;
//        循环次数
        int item = 0;
        do {
            String[] result = getStocksAllNowPrice(allStocks.subList(item * 100, (item + 1) * 100 < allStocks.size() ? (item + 1) * 100 : allStocks.size()));
            List<StockRecord> srList = new ArrayList<>();
            for (int i = 0; i < result.length; i++) {
                //            删除最后一个元素，因为最后一个是换行符
                if (i == result.length - 1)
                    break;

                String[] resultItem = result[i].split(",");
                try {
                    StockRecord sr = new StockRecord();
                    sr.setBeginPrice(Float.valueOf(resultItem[1]));
                    sr.setEndPrice(Float.valueOf(resultItem[3]));
                    sr.setHighPrice(Float.valueOf(resultItem[4]));
                    sr.setLowPrice(Float.valueOf(resultItem[5]));
                    sr.setSymbol(allStocks.get((item * 100) + i).getSymbol());
                    sr.setSname(allStocks.get((item * 100) + i).getSname());
                    sr.setRecordTime(new Date());
                    srList.add(sr);
                } catch (Exception e) {
                    System.out.println("===========================================" + resultItem + "======================================================");
                }
            }
//            批量插入数据库
            int resultInsertStrockRecord = timerMapper.insertStockRecord(srList);
            last = (item + 1) * 100;
            item++;
        } while (last < allStocks.size());

    }

    //    批量查询
    public String[] getStocksAllNowPrice(List<Stock> stocks) {
        HashMap<String, String> params = new HashMap<>();
        String stockNumStr = "";
        for (Stock stock : stocks) {
            stockNumStr += stock.getSymbol() + ",";
        }
        stockNumStr = stockNumStr.substring(0, stockNumStr.length() - 1);
        params.put("list", stockNumStr);
        String resultStr = HttpUtils.get("http://hq.sinajs.cn", params);
        String result[] = resultStr.split(";");
        return result;
    }
}
