package com.example.demo.controller;

import com.example.demo.callback.R;
import com.example.demo.domain.dto.BatchDeleteTestGroup;
import com.example.demo.domain.dto.BatchGroupItemEditDto;
import com.example.demo.domain.dto.BatchInsertGroupItemDto;
import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.TestGroup;
import com.example.demo.service.GroupItemService;
import com.example.demo.service.TestGroupService;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 * @author p78o2
 * @date 2020/9/1
 */
@RestController
@RequestMapping(value = "/stock/testGroup")
public class TestGroupController {
    @Autowired
    private TestGroupService testGroupService;
    @Autowired
    private GroupItemService groupItemService;

    @GetMapping(value = "/getAllTestGroup")
    public R getAllTestGroup(@RequestParam int userId){
        return new R (true,R.REQUEST_SUCCESS,testGroupService.getAllTestGroup(userId),"查询成功");
    }

    @PostMapping(value = "/ioeTestGroup")
    public R ioeTestGroup(@RequestBody TestGroup testGroup){
        int result = testGroupService.ioeTestGroup(testGroup);
        if(result == -1){
            return new R (false,R.REQUEST_FAIL,null,"回测分组不能重名");
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

    @GetMapping(value = "/getGroupItem")
    public R getGroupItem(@RequestParam int groupId,@RequestParam int cate){
        return new R(true,R.REQUEST_SUCCESS,groupItemService.getGroupItem(groupId,cate),"查询成功");
    }

    @GetMapping(value = "/getGroupDetail")
    public R getGroupDetail(@RequestParam int itemId){
        return new R(true,R.REQUEST_SUCCESS,groupItemService.getGroupDetail(itemId),"查询成功");
    }

    @GetMapping(value = "/getStockSearch")
    public R getStockSearch(@RequestParam String stockNum){
        return new R(true,R.REQUEST_SUCCESS,groupItemService.getStockSearch(stockNum),"查询成功");
    }

    @GetMapping(value = "/getStockHistory")
    public R getStockHistory(@RequestParam String stockNum,@RequestParam long beginDate){
        return new R(true,R.REQUEST_SUCCESS,groupItemService.getStockHistory(stockNum,beginDate),"查询成功");
    }

    @PostMapping(value = "/ioeGroupItem")
    public R ioeGroupItem(@RequestBody GroupItem groupItem){
        int result = groupItemService.ioeGroupItem(groupItem);
        if(result == 1){
            return new R (true,R.REQUEST_SUCCESS,null,"操作成功");
        }else{
            return new R (false,R.REQUEST_FAIL,null,"操作失败");
        }
    }

    @PostMapping(value = "/batchDeleteGroupItem")
    public R batchDeleteGroupItem(@RequestBody BatchGroupItemEditDto dto){
        int result = groupItemService.batchDeleteGroupItem(dto);
        if(result == 1){
            return new R (true,R.REQUEST_SUCCESS,null,"操作成功");
        }else{
            return new R (false,R.REQUEST_FAIL,null,"操作失败");
        }
    }

    @PostMapping(value = "/batchEditBeginTimeGroupItem")
    public R batchEditBeginTimeGroupItem(@RequestBody BatchGroupItemEditDto dto){
        int result = groupItemService.batchEditBeginTimeGroupItem(dto);
        if(result == 1){
            return new R (true,R.REQUEST_SUCCESS,null,"操作成功");
        }else{
            return new R (false,R.REQUEST_FAIL,null,"操作失败");
        }
    }

    @PostMapping(value = "/batchEditEndTimeGroupItem")
    public R batchEditEndTimeGroupItem(@RequestBody BatchGroupItemEditDto dto){
        int result = groupItemService.batchEditEndTimeGroupItem(dto);
        if(result == 1){
            return new R (true,R.REQUEST_SUCCESS,null,"操作成功");
        }else{
            return new R (false,R.REQUEST_FAIL,null,"操作失败");
        }
    }

    @PostMapping(value = "/batchInsertGroupItem")
    public R batchInsertGroupItem(@RequestBody BatchInsertGroupItemDto dto){
        int result = groupItemService.batchInsertGroupItem(dto);
        if(result == 1){
            return new R (true,R.REQUEST_SUCCESS,null,"操作成功");
        }else{
            return new R (false,R.REQUEST_FAIL,null,"操作失败");
        }
    }
}
