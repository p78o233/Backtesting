package com.example.demo.controller;

import com.example.demo.callback.R;
import com.example.demo.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author p78o2
 * @date 2020/9/3
 */
@RestController
@RequestMapping(value = "/stock/index")
public class IndexController {
    @Autowired
    private IndexService indexService;
    @GetMapping(value = "/getIndexVo")
    public R getIndexVo(@RequestParam int userId){
        return new R(true,R.REQUEST_SUCCESS,indexService.getIndexVo(userId),"查询成功");
    }
}
