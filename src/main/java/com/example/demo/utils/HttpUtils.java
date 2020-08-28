package com.example.demo.utils;/*
 * @author p78o2
 * @date 2019/8/28
 */

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;

public class HttpUtils {
    public static String get(String url, HashMap<String,String> params) {
        String requestUrl = url+"?";
        for (String key : params.keySet()) {
            requestUrl+=key+"="+params.get(key)+"&";
        }
        requestUrl = requestUrl.substring(0,requestUrl.length() - 1);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String resultStr = response.body().string();
            return resultStr;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
