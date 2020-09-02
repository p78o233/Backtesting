package com.example.demo.service;
/*
 * @author p78o2
 * @date 2020/9/1
 */

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.dto.BatchGroupItemEditDto;
import com.example.demo.domain.dto.BatchInsertGroupItemDto;
import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.Stock;
import com.example.demo.domain.vo.GroupItemVo;

import java.util.List;

public interface GroupItemService {
//    获取分组下的回测数据列表
    public List<GroupItemVo> getGroupItem(int groupId, int cate);
//    获取分组下的回测数据详细
    public GroupItemVo getGroupDetail(int itemId);
//    根据股票编号模糊查询股票列表
    public List<Stock> getStockSearch(String stockNum);
//    根据股票编号以及日期获取当天数据
    public JSONObject getStockHistory(String stockNum,long dateBegin);
//    单个添加或者修改回测数据
    public int ioeGroupItem(GroupItem groupItem);
//    批量删除回测分组内数据
    public int batchDeleteGroupItem(BatchGroupItemEditDto dto);
//    批量修改回测分组内开始时间
    public int batchEditBeginTimeGroupItem(BatchGroupItemEditDto dto);
//    批量修改回测分组内结束时间
    public int batchEditEndTimeGroupItem(BatchGroupItemEditDto dto);
//    批量新增回测分组内数据
    public int batchInsertGroupItem(BatchInsertGroupItemDto dto);
}
