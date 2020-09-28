package com.example.demo.controller;

import com.example.demo.service.TimerService;
import com.example.demo.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @author p78o2
 * @date 2020/8/31
 */
@RestController
@RequestMapping(value = "/stock/timmer")
public class TimmerController {
    @Autowired
    private TimerService timerService;

    private boolean workDay = true;

    //    每日获取股票列表
    @GetMapping(value = "/insertStock")
    @Scheduled(cron = "0 0 2 * * ? ")
    public void insertStock() {
        if (workDay) {
            timerService.insertStock();
        }
    }

    //    删除全部基金数据
    @GetMapping(value = "/deleteFund")
    public void deleteFund() {
        timerService.deleteFund();
    }

    //    每日获取收盘价格
    @GetMapping(value = "/getDailyRecord")
    @Scheduled(cron = "0 5 15 * * ? ")
    public void getDailyRecord() {
        if (workDay) {
            timerService.getDailyRecord();
        }
    }

    //    默认分组缓存，9到15点才执行
    @GetMapping(value = "/defaultItem")
    @Scheduled(cron = "0 0 9,10,11,12,13,14,15 * * ? ")
    public void defaultItem() {
        if (workDay) {
            timerService.defaultItem();
        }
    }

    @GetMapping(value = "/getWorkDay")
    @Scheduled(cron = "0 0 1 * * ? ")
    public void getWorkDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String beginDateStr = format.format(new Date().getTime());
        this.setWorkDay(ApiUtils.getWorkDay(beginDateStr));
    }

    public TimerService getTimerService() {
        return timerService;
    }

    public void setTimerService(TimerService timerService) {
        this.timerService = timerService;
    }

    public boolean isWorkDay() {
        return workDay;
    }

    public void setWorkDay(boolean workDay) {
        this.workDay = workDay;
    }
}
