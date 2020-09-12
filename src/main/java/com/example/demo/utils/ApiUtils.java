package com.example.demo.utils;
/*
 * @author p78o2
 * @date 2020/9/8
 */

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ApiUtils {
    private static String key = "53887";
    private static String sign = "8f409c43ad3ef74610e5c95437a82018";

    //    第三方接口查询单个查询实时价格
    public static float getStockNowPrice(String stockNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("app", "finance.stock_realtime");
        params.put("symbol", stockNum);
        params.put("appkey", key);
        params.put("sign", sign);
        String httpStr = HttpUtils.get("http://api.k780.com", params);
        JSONObject httpObj = JSONObject.parseObject(httpStr);
        if(httpObj.getString("success").equals("0")){
            return 0.0f;
        }
        JSONObject resultObj = httpObj.getJSONObject("result");
        JSONObject lists = resultObj.getJSONObject("lists");
        JSONObject obj = lists.getJSONObject(stockNum);
        return obj.getFloat("last_price");
    }

    //    第三方接口查询单个历史数据
    public static float getStockHistoryPrice(long endTime, String stockNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("app", "finance.stock_history");
        params.put("symbol", stockNum);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String endTimeStr = format.format(endTime * 1000);
        String jsonKey = format1.format(endTime * 1000);
        params.put("date", endTimeStr);
        params.put("appkey", key);
        params.put("sign", sign);
        String httpStr = HttpUtils.get("http://api.k780.com", params);
        JSONObject httpObj = JSONObject.parseObject(httpStr);
        if(httpObj.getString("success").equals("0")){
            return 0.0f;
        }
        JSONObject resultObj = httpObj.getJSONObject("result");
        JSONObject listObj = resultObj.getJSONObject("lists");
        JSONObject obj = listObj.getJSONObject(jsonKey);
        return obj.getFloat("last_price");
    }

    //    第三方接口查询昨日收盘数据
    public static float getStockYesterdayPrice(String stockNum) {
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

//    获取今天是否工作日
    public static Boolean getWorkDay(String date){
        HashMap<String, String> params = new HashMap<>();
        params.put("app", "life.workday");
        params.put("date", date);
        params.put("appkey", key);
        params.put("sign", sign);
        params.put("format", "json");
        String httpStr = HttpUtils.get("http://api.k780.com", params);
        JSONObject httpObj = JSONObject.parseObject(httpStr);
        JSONObject resultObj = httpObj.getJSONObject("result");
        if(resultObj.getString("workmk").equals("1")){
            return true;
        }else if(resultObj.getString("workmk").equals("2")){
            return false;
        }else {
            return null;
        }

    }

    //    单个查询新浪接口
    public static Float getStockNowPriceSina(String stockNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("list", stockNum);
        String resultStr = HttpUtils.get("http://hq.sinajs.cn", params);
        String result[] = resultStr.split(",");
        if(result.length<4)
            return 0.0f;
        return Float.valueOf(result[3]);
    }
}
