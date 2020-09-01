package com.example.demo.controller;

import com.example.demo.callback.R;
import com.example.demo.domain.dto.BatchDeleteTestGroup;
import com.example.demo.domain.po.TestGroup;
import com.example.demo.service.TestGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 * @author p78o2
 * @date 2020/9/1
 */
@RestController
@RequestMapping(value = "/testGroup")
public class TestGroupController {
    @Autowired
    private TestGroupService testGroupService;

    @GetMapping(value = "/getAllTestGroup")
    public R getAllTestGroup(@RequestParam int userId){
        return new R (true,R.REQUEST_SUCCESS,testGroupService.getAllTestGroup(userId),"查询成功");
    }

    @PostMapping(value = "/ioeTestGroup")
    public R ioeTestGroup(@RequestBody TestGroup testGroup){
        int result = testGroupService.ioeTestGroup(testGroup);
        if(result == -1){
            return new R (false,R.USER_REGISTER_FAIL,null,"回测分组不能重名");
        }else if(result == 1){
            return new R (true,R.REQUEST_SUCCESS,null,"操作成功");
        }else {
            return new R (false,R.REQUEST_FAIL,null,"操作失败");
        }
    }

    @PostMapping(value = "/batchDeleteTestGroup")
    public R batchDeleteTestGroup(@RequestBody BatchDeleteTestGroup dto){
        int result = testGroupService.batchDeleteTestGroup(dto);
        if(result == 1){
            return new R (true,R.REQUEST_SUCCESS,null,"操作成功");
        }else{
            return new R (false,R.REQUEST_FAIL,null,"操作失败");
        }
    }
}
