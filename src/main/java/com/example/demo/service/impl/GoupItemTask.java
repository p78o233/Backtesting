package com.example.demo.service.impl;
/*
 * @author p78o2
 * @date 2020/9/23
 */

import com.example.demo.controller.MyApplicationContextAware;
import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.StockRecord;
import com.example.demo.domain.vo.GroupItemVo;
import com.example.demo.mapper.GroupItemMapper;
import com.example.demo.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Service
public class GoupItemTask implements Callable<GroupItemVo> {
    private GroupItemMapper groupItemMapper = (GroupItemMapper) MyApplicationContextAware.getBean("groupItemMapper");;

   private GroupItem item;

    public GroupItemMapper getGroupItemMapper() {
        return groupItemMapper;
    }

    public void setGroupItemMapper(GroupItemMapper groupItemMapper) {
        this.groupItemMapper = groupItemMapper;
    }

    public GroupItem getItem() {
        return item;
    }

    public void setItem(GroupItem item) {
        this.item = item;
    }

    @Override
    public GroupItemVo call() throws Exception {
        GroupItemVo vo = new GroupItemVo();
//            获取当前价格,第三方接口
        vo.setId(item.getId());
        vo.setBuyTime(item.getBuyTime());
        vo.setBuyNum(item.getBuyNum());
        vo.setSymbol(item.getSymbol());
        vo.setBuyPrice(item.getBuyPrice());
        vo.setSname(item.getSname());
        vo.setGroupId(item.getGroupId());
        vo.setIsdel(item.getIsdel());
        vo.setCreateTime(item.getCreateTime());
        vo.setModifyTime(item.getModifyTime());
        vo.setUserId(item.getUserId());
        vo.setEndTime(item.getEndTime());
        Float nowPrice = 0.0f;
//            有设置停止时间就查历史记录,没设置就查最新记录
        //        检查是否默认分组
        if(groupItemMapper.getIsDefault(item.getGroupId()) == 1){
//            是默认分组
            if (item.getEndTime() != 0) {
//                    有限制时间长度
//                    1  先找数据库数据有没有记录
//                    2  没有数据就直接返回null
                Float recordPrice = groupItemMapper.getStockRecordByDay(item.getSymbol(),item.getEndTime());
                if(recordPrice != null){
                    nowPrice = recordPrice;
                }else {
                    nowPrice = null;
                }
            } else {
//                    没有限制观察时长查看新浪接口
                nowPrice =  ApiUtils.getStockNowPriceSina(item.getSymbol());
            }
        }else{
//            不是默认分组
            if (item.getEndTime() != 0) {
//                    有限制时间长度
//                    1  先找数据库数据有没有记录
//                    2  没有揭露就去查接口
                Float recordPrice = groupItemMapper.getStockRecordByDay(item.getSymbol(),item.getEndTime());
                if(recordPrice != null){
                    nowPrice = recordPrice;
                }else {
                    nowPrice = ApiUtils.getStockHistoryPrice(item.getEndTime(), item.getSymbol());
                }
            } else {
//                    没有限制观察时长查看新浪接口
                nowPrice =  ApiUtils.getStockNowPriceSina(item.getSymbol());
            }
        }
        vo.setNowPrice(nowPrice);
        if(nowPrice != null) {
            vo.setProfit((nowPrice - item.getBuyPrice()) * item.getBuyNum());
        }else{
            vo.setProfit(0.0f);
        }
        DecimalFormat decimalFormat = new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        if(item.getBuyPrice() != 0.0f) {
            String p = "";
            if(nowPrice != null && item.getBuyPrice() != 0 ) {
                p = decimalFormat.format(((nowPrice / item.getBuyPrice()) - 1) * 100);//format 返回的是字符串
            }else{
                p = "0.0";
            }
            vo.setProfitPencent(p + "%");
        }else{
            vo.setProfitPencent(0.0 + "%");
        }
        float p = 0.0f;
        if(nowPrice != null && item.getBuyPrice() != 0 ) {
            p =((nowPrice / item.getBuyPrice()) - 1) * 100;
        }else{
            p = 0.0f;
        }
        vo.setProfitPencentNum(p);
//            检查在回测日期记录内涨跌平数据
        List<StockRecord> list = new ArrayList<>();
        if(item.getEndTime()==0){
            list = groupItemMapper.getAllStockEndTimeNone(item.getSymbol(),item.getBuyTime());
        }else{
            list = groupItemMapper.getAllStockEndTime(item.getSymbol(),item.getBuyTime(),item.getEndTime());
        }
        int raiseDays = 0;
        int dropDays = 0;
        int blanceDays = 0;
        for(StockRecord sr : list){
            if(sr.getBeginPrice()>sr.getEndPrice()){
                dropDays+=1;
            }else if(sr.getBeginPrice() == sr.getEndPrice()){
                blanceDays += 1;
            }else{
                raiseDays += 1;
            }
        }
        vo.setTotalDays(list.size());
        vo.setRaiseDays(raiseDays);
        vo.setDropDays(dropDays);
        vo.setBlanceDays(blanceDays);
        return vo;
    }
}
