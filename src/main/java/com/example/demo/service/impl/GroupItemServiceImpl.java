package com.example.demo.service.impl;
/*
 * @author p78o2
 * @date 2020/9/1
 */

import com.alibaba.fastjson.JSONObject;
import com.example.demo.callback.PageInfo;
import com.example.demo.callback.R;
import com.example.demo.domain.dto.BatchGroupItemEditDto;
import com.example.demo.domain.dto.BatchInsertGroupItemDto;
import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.Stock;
import com.example.demo.domain.po.StockRecord;
import com.example.demo.domain.vo.GroupItemVo;
import com.example.demo.mapper.GroupItemMapper;
import com.example.demo.service.GroupItemService;
import com.example.demo.utils.ApiUtils;
import com.example.demo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@Service
public class GroupItemServiceImpl implements GroupItemService {
    @Autowired
    private GroupItemMapper groupItemMapper;
    @Value("${testAppKey}")
    private String key;
    @Value("${testSign}")
    private String sign;

    @Override
    public PageInfo<GroupItemVo> getGroupItem(int groupId, int category, int cateNowPrice, int page, int pageSize) throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        int start = (page - 1) * pageSize;
//        总行数
        int count = 0;
        count = groupItemMapper.getGroupAllItemCount(groupId);
//        检查是否默认分组
        if(groupItemMapper.getIsDefault(groupId) == 1){
//            是默认分组就就查询defaultitem
            List<GroupItemVo> resultList = new ArrayList<>();
            if(cateNowPrice == 1){
                resultList = groupItemMapper.getListNowPriceASC(groupId,start,pageSize);
            }else if(cateNowPrice == -1){
                resultList = groupItemMapper.getListNowPriceNumDESC(groupId,start,pageSize);
            }

            if(category == 1){
                resultList = groupItemMapper.getListProfitPencentNumASC(groupId,start,pageSize);
            }else if(category == -1){
                resultList = groupItemMapper.getListProfitPencentNumDESC(groupId,start,pageSize);
            }
            return new PageInfo<GroupItemVo>(count, resultList);
        }




        List<GroupItem> groupItems = new ArrayList<>();
        groupItems = groupItemMapper.getGroupAllItemsNoPage(groupId);
//        返回的列表
        long loopStart = System.currentTimeMillis();

        ExecutorService executor = Executors.newCachedThreadPool();
        List<GroupItemVo> groupItemVos = new ArrayList<GroupItemVo>();

        for (GroupItem item : groupItems) {

            GroupItemVo vo = new GroupItemVo();
            GoupItemTask task = new GoupItemTask();
            task.setItem(item);
            Future<GroupItemVo> res = executor.submit(task);//异步提交, non blocking.
            groupItemVos.add(res.get());
////            获取当前价格,第三方接口
//            vo.setId(item.getId());
//            vo.setBuyTime(item.getBuyTime());
//            vo.setBuyNum(item.getBuyNum());
//            vo.setSymbol(item.getSymbol());
//            vo.setBuyPrice(item.getBuyPrice());
//            vo.setSname(item.getSname());
//            vo.setGroupId(item.getGroupId());
//            vo.setIsdel(item.getIsdel());
//            vo.setCreateTime(item.getCreateTime());
//            vo.setModifyTime(item.getModifyTime());
//            vo.setUserId(item.getUserId());
//            vo.setEndTime(item.getEndTime());
//            Float nowPrice = 0.0f;
////            有设置停止时间就查历史记录,没设置就查最新记录
//            //        检查是否默认分组
//            if(groupItemMapper.getIsDefault(groupId) == 1){
////            是默认分组
//                if (item.getEndTime() != 0) {
////                    有限制时间长度
////                    1  先找数据库数据有没有记录
////                    2  没有数据就直接返回null
//                    Float recordPrice = groupItemMapper.getStockRecordByDay(item.getSymbol(),item.getEndTime());
//                    if(recordPrice != null){
//                        nowPrice = recordPrice;
//                    }else {
//                        nowPrice = null;
//                    }
//                } else {
////                    没有限制观察时长查看新浪接口
//                    nowPrice =  ApiUtils.getStockNowPriceSina(item.getSymbol());
//                }
//            }else{
////            不是默认分组
//                if (item.getEndTime() != 0) {
////                    有限制时间长度
////                    1  先找数据库数据有没有记录
////                    2  没有揭露就去查接口
//                    Float recordPrice = groupItemMapper.getStockRecordByDay(item.getSymbol(),item.getEndTime());
//                    if(recordPrice != null){
//                        nowPrice = recordPrice;
//                    }else {
//                        nowPrice = ApiUtils.getStockHistoryPrice(item.getEndTime(), item.getSymbol());
//                    }
//                } else {
////                    没有限制观察时长查看新浪接口
//                    nowPrice =  ApiUtils.getStockNowPriceSina(item.getSymbol());
//                }
//            }
//            vo.setNowPrice(nowPrice);
//            if(nowPrice != null) {
//                vo.setProfit((nowPrice - item.getBuyPrice()) * item.getBuyNum());
//            }else{
//                vo.setProfit(0.0f);
//            }
//            DecimalFormat decimalFormat = new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//            if(item.getBuyPrice() != 0.0f) {
//                String p = "";
//                if(nowPrice != null && item.getBuyPrice() != 0 ) {
//                     p = decimalFormat.format(((nowPrice / item.getBuyPrice()) - 1) * 100);//format 返回的是字符串
//                }else{
//                    p = "0.0";
//                }
//                vo.setProfitPencent(p + "%");
//            }else{
//                vo.setProfitPencent(0.0 + "%");
//            }
//            float p = 0.0f;
//            if(nowPrice != null && item.getBuyPrice() != 0 ) {
//                p =((nowPrice / item.getBuyPrice()) - 1) * 100;
//            }else{
//                p = 0.0f;
//            }
//            vo.setProfitPencentNum(p);
////            检查在回测日期记录内涨跌平数据
//            List<StockRecord> list = new ArrayList<>();
//            if(item.getEndTime()==0){
//                list = groupItemMapper.getAllStockEndTimeNone(item.getSymbol(),item.getBuyTime());
//            }else{
//                list = groupItemMapper.getAllStockEndTime(item.getSymbol(),item.getBuyTime(),item.getEndTime());
//            }
//            int raiseDays = 0;
//            int dropDays = 0;
//            int blanceDays = 0;
//            for(StockRecord sr : list){
//                if(sr.getBeginPrice()>sr.getEndPrice()){
//                    dropDays+=1;
//                }else if(sr.getBeginPrice() == sr.getEndPrice()){
//                    blanceDays += 1;
//                }else{
//                    raiseDays += 1;
//                }
//            }
//            vo.setTotalDays(list.size());
//            vo.setRaiseDays(raiseDays);
//            vo.setDropDays(dropDays);
//            vo.setBlanceDays(blanceDays);
//
//            groupItemVos.add(vo);
        }
        executor.shutdown();
        long loopEnd = System.currentTimeMillis();

        //        现价排序
        if (cateNowPrice == 1) {
//            升序

            Collections.sort(groupItemVos, new Comparator<GroupItemVo>() {
                @Override
                public int compare(GroupItemVo vo1, GroupItemVo vo2) {
                    float diff = (vo1.getNowPrice()) - (vo2.getNowPrice());
                    if (diff > 0) {
                        return 1;
                    } else if (diff < 0) {
                        return -1;
                    }
                    return 0; //相等为0
                }
            });
        } else if (cateNowPrice == -1) {
//            降序
            Collections.sort(groupItemVos, new Comparator<GroupItemVo>() {
                @Override
                public int compare(GroupItemVo vo1, GroupItemVo vo2) {
                    float diff = (vo1.getNowPrice()) - (vo2.getNowPrice());
                    if (diff > 0) {
                        return -1;
                    } else if (diff < 0) {
                        return 1;
                    }
                    return 0; //相等为0
                }
            });
        }

        //        盈亏百分比排序
        if (category == 1) {
//            升序
            Collections.sort(groupItemVos, new Comparator<GroupItemVo>() {
                @Override
                public int compare(GroupItemVo vo1, GroupItemVo vo2) {
                    float diff = (vo1.getProfitPencentNum()) - (vo2.getProfitPencentNum());
                    if (diff > 0) {
                        return 1;
                    } else if (diff < 0) {
                        return -1;
                    }
                    return 0; //相等为0
                }
            });
        } else if (category == -1) {
//            降序
            Collections.sort(groupItemVos, new Comparator<GroupItemVo>() {
                @Override
                public int compare(GroupItemVo vo1, GroupItemVo vo2) {
                    float diff = (vo1.getProfitPencentNum()) - (vo2.getProfitPencentNum());
                    if (diff > 0) {
                        return -1;
                    } else if (diff < 0) {
                        return 1;
                    }
                    return 0; //相等为0
                }
            });
        }

        if(groupItemMapper.getIsDefault(groupId) == 1){
//            默认分组不分页直接返回了
            return new PageInfo<GroupItemVo>(count, groupItemVos);
        }

        if (start < groupItemVos.size()) {
            if (start + pageSize > groupItemVos.size()) {
                groupItemVos = groupItemVos.subList(start, groupItemVos.size() - 1);
            } else {
                groupItemVos = groupItemVos.subList(start, start + pageSize);
            }
        } else {
            return null;
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        System.out.println("loog:" + String.valueOf(loopEnd - loopStart));
        return new PageInfo<GroupItemVo>(count, groupItemVos);


    }

    @Override
    public GroupItemVo getGroupDetail(int itemId) {
        GroupItem item = new GroupItem();
        item = groupItemMapper.getGroupItemDetail(itemId);
        GroupItemVo vo = new GroupItemVo();
//            获取当前价格,第三方接口
        vo.setId(item.getId());
        vo.setBuyTime(item.getBuyTime());
        vo.setBuyNum(item.getBuyNum());
        vo.setBuyTime(item.getBuyTime());
        vo.setSymbol(item.getSymbol());
        vo.setSname(item.getSname());
        vo.setGroupId(item.getGroupId());
        vo.setIsdel(item.getIsdel());
        vo.setCreateTime(item.getCreateTime());
        vo.setModifyTime(item.getModifyTime());
        vo.setUserId(item.getUserId());
        vo.setEndTime(item.getEndTime());
        float nowPrice = 0.0f;
//            有设置停止时间就查历史记录,没设置就查最新记录
        if (item.getEndTime() != 0) {
            nowPrice = ApiUtils.getStockHistoryPrice(item.getEndTime(), item.getSymbol());
        } else {
            nowPrice = ApiUtils.getStockNowPrice(item.getSymbol());
        }
        vo.setNowPrice(nowPrice);
        vo.setProfit((nowPrice - item.getBuyNum()) * item.getBuyNum());
        DecimalFormat decimalFormat = new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(((nowPrice / item.getBuyPrice()) - 1) * 100);//format 返回的是字符串
        vo.setProfitPencent(p + "%");
        return vo;
    }


    @Override
    public List<Stock> getStockSearch(String stockNum) {
        return groupItemMapper.getStockSearch(stockNum);
    }

    @Override
    public JSONObject getStockHistory(String stockNum, long dateBegin) {
//        返回对象
        JSONObject retrunJson = new JSONObject();


        HashMap<String, String> params = new HashMap<>();
        params.put("app", "finance.stock_history");
        params.put("symbol", stockNum);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String beginDateStr = format.format(dateBegin * 1000);
        String jsonKey = format1.format(dateBegin * 1000);
        params.put("date", beginDateStr);
        params.put("appkey", key);
        params.put("sign", sign);
        String httpStr = HttpUtils.get("http://api.k780.com", params);
        JSONObject httpObj = JSONObject.parseObject(httpStr);
        if (httpObj.getString("success").equals("0")) {
            retrunJson.put("last_price", 0.0f);
            retrunJson.put("rate", "0.0%");
            return retrunJson;
        }
        JSONObject resultObj = httpObj.getJSONObject("result");
        JSONObject listObj = resultObj.getJSONObject("lists");
        JSONObject obj = listObj.getJSONObject(jsonKey);

        float nowPrice = obj.getFloat("last_price");
        float yesterdayPrice = obj.getFloat("yesy_price");
        DecimalFormat decimalFormat = new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(((nowPrice / yesterdayPrice) - 1) * 100);//format 返回的是字符串
        retrunJson.put("last_price", nowPrice);
        retrunJson.put("rate", p + "%");
        return retrunJson;
    }

    @Override
    public int ioeGroupItem(GroupItem groupItem) {
        if (groupItem.getId() == null) {
//            新增
            groupItem.setCreateTime(new Date());
            groupItem.setModifyTime(new Date());
            if (groupItemMapper.insertGroupItem(groupItem) > 0)
                return 1;
            return 0;
        } else {
//            修改
            groupItem.setModifyTime(new Date());
            if (groupItemMapper.updateGroupItem(groupItem) > 0)
                return 1;
            return 0;
        }
    }

    @Override
    public int batchDeleteGroupItem(BatchGroupItemEditDto dto) {
//        检查这个id的组是不是默认分组
        if (groupItemMapper.getIsDefault(dto.getGroupId()) == 1) {
            return -1;
        }
        if (groupItemMapper.batchDeleteAllGroupItem(dto.getItemIds()) > 0)
            return 1;
        return 0;
    }

    @Override
    public R batchEditBeginTimeGroupItem(BatchGroupItemEditDto dto) {
//        检查这个分组是否默认分组
        if (groupItemMapper.getIsDefault(dto.getGroupId()) == 1) {
//            默认分组
            List<Integer> list = new ArrayList<>();
//            检查每一个的item里面的id的每个回测日期都大于创建的日期
            for (Integer index : dto.getItemIds()) {
//                获取每个股票的创建时间
                Date beginTime = groupItemMapper.getStockBySymbol(index);
                if (dto.getBeginTime() < (beginTime.getTime() / 1000)) {
//                    回测时间比创建时间早
                    list.add(index);
                } else {
                    GroupItem item = new GroupItem();
//                    不查第三方接口查自己的记录
                    item.setBuyPrice(groupItemMapper.getEndPrice(groupItemMapper.getSymbolByItemId(index), dto.getBeginTime()) == null ?
                            0.0f : groupItemMapper.getEndPrice(groupItemMapper.getSymbolByItemId(dto.getItemIds().get(index)), dto.getBeginTime()));
                    item.setBuyTime(dto.getBeginTime());
                    item.setId(index);
                    groupItemMapper.batchEditBeginTimeGroupItem(item);
                }
            }
            if (list.size() > 0) {
                return new R(true, R.SOME_ERROR, list, "起始时间不能小于股票记录初始时间");
            } else {
                return new R(true, R.REQUEST_SUCCESS, null, "操作成功");
            }
        } else {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < dto.getItemIds().size(); i++) {
                GroupItem item = new GroupItem();
                item.setBuyPrice(ApiUtils.getStockHistoryPrice(dto.getBeginTime(), groupItemMapper.getSymbolByItemId(dto.getItemIds().get(i))));
                item.setBuyTime(dto.getBeginTime());
                item.setId(dto.getItemIds().get(i));
                if (groupItemMapper.countBuyBiggerEnd(dto.getBeginTime(), dto.getItemIds().get(i)) > 0) {
                    list.add(dto.getItemIds().get(i));
                } else {
                    groupItemMapper.batchEditBeginTimeGroupItem(item);
                }
            }
            if (list.size() > 0) {
                return new R(true, R.SOME_ERROR, list, "起始时间不能大于结束时间");
            } else {
                return new R(true, R.REQUEST_SUCCESS, null, "操作成功");
            }
        }
    }

    @Override
    public R batchEditEndTimeGroupItem(BatchGroupItemEditDto dto) {
        //        检查这个分组是否默认分组
        if (groupItemMapper.getIsDefault(dto.getGroupId()) == 1) {
//            默认分组
            List<Integer> list = new ArrayList<>();
//            检查每一个的item里面的id的每个回测日期都大于创建的日期
            for (Integer index : dto.getItemIds()) {
//                获取每个股票的创建时间
                Date beginTime = groupItemMapper.getStockBySymbol(index);
                if (dto.getEndTime() < (beginTime.getTime() / 1000)) {
//                    回测时间比创建时间早
                    list.add(index);
                }
            }
            if (list.size() > 0) {
                return new R(true, R.SOME_ERROR, list, "结束时间不能小于股票记录初始时间");
            } else {
                return new R(true, R.REQUEST_SUCCESS, null, "操作成功");
            }
        }

        if (dto.getEndTime() == 0) {
//            无时间限制
            if (groupItemMapper.batchEditEndTimeGroupItem(dto.getEndTime(), dto.getItemIds()) > 0)
                return new R(true, R.REQUEST_SUCCESS, null, "操作成功");
            return new R(true, R.REQUEST_FAIL, null, "操作失败");
        } else {
//            有时间限制
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < dto.getItemIds().size(); i++)
                if (groupItemMapper.countEndSmallerBuy(dto.getEndTime(), dto.getItemIds().get(i)) > 0) {
                    list.add(dto.getItemIds().get(i));
                }
            if (list.size() > 0) {
                return new R(true, R.SOME_ERROR, list, "结束时间不能早于开始时间");
            } else {
                return new R(true, R.REQUEST_SUCCESS, null, "操作成功");
            }
        }
    }

    @Override
    public int batchInsertGroupItem(BatchInsertGroupItemDto dto) {
        List<GroupItem> list = new ArrayList<>();
        for (int i = 0; i < dto.getSymbol().size(); i++) {
            GroupItem item = new GroupItem();
            item.setBuyTime(dto.getBuyTime());
            item.setBuyNum(dto.getBuyNum());
            item.setBuyPrice(ApiUtils.getStockHistoryPrice(dto.getBuyTime(), dto.getSymbol().get(i)));
            item.setSname(groupItemMapper.getSnameSymbol(dto.getSymbol().get(i)));
            item.setSymbol(dto.getSymbol().get(i));
            item.setGroupId(dto.getGroupId());
            item.setUserId(dto.getUserId());
            item.setEndTime(dto.getEndTime());
            item.setCreateTime(new Date());
            item.setModifyTime(new Date());
            list.add(item);
        }
        groupItemMapper.bacthGroupItem(list);
        return 1;
    }

    @Override
    public int batchInsertGroupItemRegister(BatchInsertGroupItemDto dto) {
        List<GroupItem> list = new ArrayList<>();
        for (int i = 0; i < dto.getSymbol().size(); i++) {
            GroupItem item = new GroupItem();
            item.setBuyTime(dto.getBuyTime());
            item.setBuyNum(dto.getBuyNum());
            item.setBuyPrice(ApiUtils.getStockNowPriceSina(dto.getSymbol().get(i)));
            item.setSname(groupItemMapper.getSnameSymbol(dto.getSymbol().get(i)));
            item.setSymbol(dto.getSymbol().get(i));
            item.setGroupId(dto.getGroupId());
            item.setUserId(dto.getUserId());
            item.setEndTime(0L);
            item.setCreateTime(new Date());
            item.setModifyTime(new Date());
            list.add(item);
            if (i % 100 == 0) {
                groupItemMapper.bacthGroupItem(list);
                list = new ArrayList<>();
            }
        }
        groupItemMapper.bacthGroupItem(list);
        return 1;
    }


}
