package com.example.demo.service.impl;
/*
 * @author p78o2
 * @date 2020/9/1
 */

import com.alibaba.fastjson.JSONObject;
import com.example.demo.callback.R;
import com.example.demo.domain.dto.BatchGroupItemEditDto;
import com.example.demo.domain.dto.BatchInsertGroupItemDto;
import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.Stock;
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

@Service
public class GroupItemServiceImpl implements GroupItemService {
    @Autowired
    private GroupItemMapper groupItemMapper;
    @Value("${testAppKey}")
    private String key;
    @Value("${testSign}")
    private String sign;

    @Override
    public List<GroupItemVo> getGroupItem(int groupId, int cate) {
        List<GroupItem> groupItems = new ArrayList<>();
        groupItems = groupItemMapper.getGroupAllItems(groupId);
        List<GroupItemVo> groupItemVos = new ArrayList<>();
        for (GroupItem item : groupItems) {
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
            groupItemVos.add(vo);
        }
        if (cate == 1) {
//            利润从大到小
            Collections.sort(groupItemVos, new Comparator<GroupItemVo>() {
                @Override
                public int compare(GroupItemVo vo1, GroupItemVo vo2) {
                    float diff = (vo1.getNowPrice()/vo1.getBuyNum()) - (vo2.getNowPrice()/vo2.getBuyNum());
                    if (diff > 0) {
                        return 1;
                    } else if (diff < 0) {
                        return -1;
                    }
                    return 0; //相等为0
                }
            });
        } else {
//            利润从小到大
            Collections.sort(groupItemVos, new Comparator<GroupItemVo>() {
                @Override
                public int compare(GroupItemVo vo1, GroupItemVo vo2) {
                    float diff = (vo1.getNowPrice()/vo1.getBuyNum()) - (vo2.getNowPrice()/vo2.getBuyNum());
                    if (diff > 0) {
                        return -1;
                    } else if (diff < 0) {
                        return 1;
                    }
                    return 0; //相等为0
                }
            });
        }
        return groupItemVos;
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
        if(httpObj.getString("success").equals("0")){
            retrunJson.put("last_price",0.0f);
            retrunJson.put("rate","0.0%");
            return retrunJson;
        }
        JSONObject resultObj = httpObj.getJSONObject("result");
        JSONObject listObj = resultObj.getJSONObject("lists");
        JSONObject obj = listObj.getJSONObject(jsonKey);

        float nowPrice = obj.getFloat("last_price");
        float yesterdayPrice = obj.getFloat("yesy_price");
        DecimalFormat decimalFormat = new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(((nowPrice / yesterdayPrice) - 1) * 100);//format 返回的是字符串
        retrunJson.put("last_price",nowPrice);
        retrunJson.put("rate",p+"%");
        return retrunJson;
    }

    @Override
    public int ioeGroupItem(GroupItem groupItem) {
        if(groupItem.getId() == null){
//            新增
            groupItem.setCreateTime(new Date());
            groupItem.setModifyTime(new Date());
            if(groupItemMapper.insertGroupItem(groupItem)>0)
                return 1;
            return 0;
        }else{
//            修改
            groupItem.setModifyTime(new Date());
            if(groupItemMapper.updateGroupItem(groupItem)>0)
                return 1;
            return 0;
        }
    }

    @Override
    public int batchDeleteGroupItem(BatchGroupItemEditDto dto) {
//        检查这个id的组是不是默认分组
        if(groupItemMapper.getIsDefault(dto.getGroupId())==1){
            return -1;
        }
        if(groupItemMapper.batchDeleteAllGroupItem(dto.getItemIds())>0)
            return 1;
        return 0;
    }

    @Override
    public R batchEditBeginTimeGroupItem(BatchGroupItemEditDto dto) {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < dto.getItemIds().size() ;i++){
            GroupItem item = new GroupItem();
            item.setBuyPrice(ApiUtils.getStockHistoryPrice(dto.getBeginTime(),groupItemMapper.getSymbolByItemId(dto.getItemIds().get(i))));
            item.setBuyTime(dto.getBeginTime());
            item.setId(dto.getItemIds().get(i));
            if(groupItemMapper.countBuyBiggerEnd(dto.getBeginTime(),dto.getItemIds().get(i))>0){
                list.add(dto.getItemIds().get(i));
            }else {
                groupItemMapper.batchEditBeginTimeGroupItem(item);
            }
        }
        if(list.size()>0){
            return new R(true,R.SOME_ERROR,list,"起始时间不能大于结束时间");
        }else{
            return new R(true,R.REQUEST_SUCCESS,null,"操作成功");
        }
    }

    @Override
    public R batchEditEndTimeGroupItem(BatchGroupItemEditDto dto) {
        if(dto.getEndTime() == 0) {
//            无时间限制
            if (groupItemMapper.batchEditEndTimeGroupItem(dto.getEndTime(), dto.getItemIds()) > 0)
                return new R(true,R.REQUEST_SUCCESS,null,"操作成功");
            return new R(true,R.REQUEST_FAIL,null,"操作失败");
        }else{
//            有时间限制
            List<Integer> list = new ArrayList<>();
            for(int i = 0;i<dto.getItemIds().size();i++)
            if(groupItemMapper.countEndSmallerBuy(dto.getEndTime(),dto.getItemIds().get(i))>0){
                list.add(dto.getItemIds().get(i));
            }
            if(list.size()>0){
                return new R(true,R.SOME_ERROR,list,"结束时间不能早于开始时间");
            }else{
                return new R(true,R.REQUEST_SUCCESS,null,"操作成功");
            }
        }
    }

    @Override
    public int batchInsertGroupItem(BatchInsertGroupItemDto dto) {
        List<GroupItem> list = new ArrayList<>();
        for(int i = 0 ;i < dto.getSymbol().size();i++){
            GroupItem item = new GroupItem();
            item.setBuyTime(dto.getBuyTime());
            item.setBuyNum(dto.getBuyNum());
            item.setBuyPrice(ApiUtils.getStockHistoryPrice(dto.getBuyTime(),dto.getSymbol().get(i)));
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


}
