package com.example.demo.controller;

import com.example.demo.callback.R;
import com.example.demo.domain.dto.BatchDeleteTestGroup;
import com.example.demo.domain.dto.BatchGroupItemEditDto;
import com.example.demo.domain.dto.BatchInsertGroupItemDto;
import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.TestGroup;
import com.example.demo.service.GroupItemService;
import com.example.demo.service.TestGroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static Logger log = LogManager.getLogger(TestGroupController.class);

    @GetMapping(value = "/getAllTestGroup")
    public R getAllTestGroup(@RequestParam int userId){
        log.info("获取全部分组userId~"+userId);
        return new R (true,R.REQUEST_SUCCESS,testGroupService.getAllTestGroup(userId),"查询成功");
    }

    @PostMapping(value = "/ioeTestGroup")
    public R ioeTestGroup(@RequestBody TestGroup testGroup){
        log.info("新增修改分组testGroup~"+testGroup.toString());
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
        log.info("批量删除分组 BatchDeleteTestGroup~"+dto.toString());
        int result = testGroupService.batchDeleteTestGroup(dto);
        if(result == 1){
            return new R (true,R.REQUEST_SUCCESS,null,"操作成功");
        }else{
            return new R (false,R.REQUEST_FAIL,null,"操作失败");
        }
    }

    @GetMapping(value = "/getGroupItem")
    public R getGroupItem(@RequestParam int groupId,@RequestParam int category,@RequestParam int cateNowPrice,@RequestParam int page,@RequestParam int pageSize){
        log.info("分页获取分组内item groupId~"+groupId+"~page:~"+page+"~pageSize~"+pageSize);
        return new R(true,R.REQUEST_SUCCESS,groupItemService.getGroupItem(groupId,category,cateNowPrice,page,pageSize),"查询成功");
    }

    @GetMapping(value = "/getGroupDetail")
    public R getGroupDetail(@RequestParam int itemId){
        log.info("item详细 itemId~"+itemId);
        return new R(true,R.REQUEST_SUCCESS,groupItemService.getGroupDetail(itemId),"查询成功");
    }

    @GetMapping(value = "/getStockSearch")
    public R getStockSearch(@RequestParam String stockNum){
        log.info("根据股票编号模糊查询股票信息 stockNum~"+stockNum);
        return new R(true,R.REQUEST_SUCCESS,groupItemService.getStockSearch(stockNum),"查询成功");
    }

    @GetMapping(value = "/getStockHistory")
    public R getStockHistory(@RequestParam String stockNum,@RequestParam long beginDate){
        log.info("获取某个股票某天的历史数据 stockNum~"+stockNum+"~beginDate~"+beginDate);
        return new R(true,R.REQUEST_SUCCESS,groupItemService.getStockHistory(stockNum,beginDate),"查询成功");
    }

    @PostMapping(value = "/ioeGroupItem")
    public R ioeGroupItem(@RequestBody GroupItem groupItem){
        log.info("新增修改分组内item：groupItem:"+groupItem.toString());
        int result = groupItemService.ioeGroupItem(groupItem);
        if(result == 1){
            return new R (true,R.REQUEST_SUCCESS,null,"操作成功");
        }else{
            return new R (false,R.REQUEST_FAIL,null,"操作失败");
        }
    }

    @PostMapping(value = "/batchDeleteGroupItem")
    public R batchDeleteGroupItem(@RequestBody BatchGroupItemEditDto dto){
        log.info("批量删除分组内item：BatchGroupItemEditDto"+dto.toString());
        int result = groupItemService.batchDeleteGroupItem(dto);
        if(result == -1){
            return new R (false,R.REQUEST_FAIL,null,"默认分组不能删除");
        }
        else if(result == 1){
            return new R (true,R.REQUEST_SUCCESS,null,"操作成功");
        }else{
            return new R (false,R.REQUEST_FAIL,null,"操作失败");
        }
    }

    @PostMapping(value = "/batchEditBeginTimeGroupItem")
    public R batchEditBeginTimeGroupItem(@RequestBody BatchGroupItemEditDto dto){
        log.info("批量修改分组内item起始时间：BatchGroupItemEditDto"+dto.toString());
        return groupItemService.batchEditBeginTimeGroupItem(dto);
    }

    @PostMapping(value = "/batchEditEndTimeGroupItem")
    public R batchEditEndTimeGroupItem(@RequestBody BatchGroupItemEditDto dto){
        log.info("批量修改分组内item结束时间：BatchGroupItemEditDto"+dto.toString());
        return groupItemService.batchEditEndTimeGroupItem(dto);
    }

    @PostMapping(value = "/batchInsertGroupItem")
    public R batchInsertGroupItem(@RequestBody BatchInsertGroupItemDto dto){
        log.info("批量新增分组内item：BatchInsertGroupItemDto"+dto.toString());
        int result = groupItemService.batchInsertGroupItem(dto);
        if(result == 1){
            return new R (true,R.REQUEST_SUCCESS,null,"操作成功");
        }else{
            return new R (false,R.REQUEST_FAIL,null,"操作失败");
        }
    }
}
