package com.example.demo.service;
/*
 * @author p78o2
 * @date 2020/8/31
 */
//定时任务
public interface TimerService {
//    每天拉取股票列表
    public void insertStock();
//    每天获取股票收盘记录
    public void getDailyRecord();
//    定时任务默认分组缓存
    public void defaultItem();
}
