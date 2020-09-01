package com.example.demo.controller;

import com.example.demo.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author p78o2
 * @date 2020/8/31
 */
@RestController
@RequestMapping(value = "/stock/timmer")
public class TimmerController {
    @Autowired
    private TimerService timerService;
//    每日获取股票列表
    @GetMapping(value = "/insertStock")
    @Scheduled(cron = "0 0 6 * * ? ")
    public void insertStock(){
        timerService.insertStock();
    }

//    每日获取收盘价格
    @GetMapping(value = "/getDailyRecord")
    @Scheduled(cron = "0 5 15 * * ? ")
    public void getDailyRecord(){
        timerService.getDailyRecord();
    }
}
