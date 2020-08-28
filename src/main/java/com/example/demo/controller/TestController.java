package com.example.demo.controller;

import com.example.demo.callback.R;
import com.example.demo.service.TestService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author p78o2
 * @date 2020/8/28
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/getAll")
    public R getALL(){
        return new R (true,200,testService.getAll(),"");
    }
}
