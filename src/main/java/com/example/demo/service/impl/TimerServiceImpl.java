package com.example.demo.service.impl;
/*
 * @author p78o2
 * @date 2020/8/31
 */

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.Stock;
import com.example.demo.domain.po.StockRecord;
import com.example.demo.domain.po.User;
import com.example.demo.domain.vo.GroupItemVo;
import com.example.demo.mapper.GroupItemMapper;
import com.example.demo.mapper.TimerMapper;
import com.example.demo.service.TimerService;
import com.example.demo.utils.ApiUtils;
import com.example.demo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class TimerServiceImpl implements TimerService {
    @Autowired
    private TimerMapper timerMapper;
    @Autowired
    private GroupItemMapper groupItemMapper;
    @Value("${testAppKey}")
    private String key;
    @Value("${testSign}")
    private String sign;
    @Autowired
    private AsyncTack asyncTack;

    @Override
    public void insertStock() {
        HashMap<String, String> params = new HashMap<>();
        params.put("app", "finance.stock_list");
        params.put("category", "hs");
        params.put("appkey", key);
        params.put("sign", sign);
        String result = HttpUtils.get("http://api.k780.com", params);
        JSONObject obj = JSONObject.parseObject(result);
        String resultStr = obj.getString("result");
        JSONObject objRestult = JSONObject.parseObject(resultStr);
        List<Stock> stocks = new ArrayList<>();
        stocks = JSONObject.parseArray(objRestult.getString("lists"), Stock.class);
//        拉取新数据去重新增入库
        try {
            int resultInsert = timerMapper.insertStock(stocks);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }

//        获取全部人的默认分组id
        List<Integer> allUserIdList = new ArrayList<>();
        allUserIdList = timerMapper.getAllUserId();
        for (int i = 0; i < allUserIdList.size(); i++) {
            int groupId = timerMapper.getGroupIdByUserId(allUserIdList.get(i));
            List<Stock> stockList = new ArrayList<>();
            stockList = timerMapper.getNoneExitStock(groupId, allUserIdList.get(i));
            asyncTack.batchLongTimeStocks(allUserIdList.get(i), groupId, stockList);
        }
    }

    @Override
    public void getDailyRecord() {
//        获取stock表的全部内容
        List<Stock> allStocks = new ArrayList<>();
        allStocks = timerMapper.getAllStock();
        int last = 0;
//        循环次数
        int item = 0;
        do {
            String[] result = getStocksAllNowPrice(allStocks.subList(item * 100, (item + 1) * 100 < allStocks.size() ? (item + 1) * 100 : allStocks.size()));
            List<StockRecord> srList = new ArrayList<>();
            for (int i = 0; i < result.length; i++) {
                //            删除最后一个元素，因为最后一个是换行符
                if (i == result.length - 1)
                    break;

                String[] resultItem = result[i].split(",");
                try {
                    StockRecord sr = new StockRecord();
                    sr.setBeginPrice(Float.valueOf(resultItem[1]));
                    sr.setEndPrice(Float.valueOf(resultItem[3]));
                    sr.setHighPrice(Float.valueOf(resultItem[4]));
                    sr.setLowPrice(Float.valueOf(resultItem[5]));
                    sr.setSymbol(allStocks.get((item * 100) + i).getSymbol());
                    sr.setSname(allStocks.get((item * 100) + i).getSname());
                    sr.setRecordTime(new Date());
                    srList.add(sr);
                } catch (Exception e) {
                    System.out.println("===========================================" + resultItem + "======================================================");
                }
            }
//            批量插入数据库
            int resultInsertStrockRecord = timerMapper.insertStockRecord(srList);
            last = (item + 1) * 100;
            item++;
        } while (last < allStocks.size());

    }

    @Override
    public void defaultItem() {
        List<Integer> groupIds = new ArrayList<>();
        groupIds = timerMapper.getGroupIds();
        List<GroupItemVo> groupItemVosInsert = new ArrayList<GroupItemVo>();
        for (Integer groupId : groupIds) {
            List<GroupItem> groupItems = new ArrayList<>();
            groupItems = groupItemMapper.getGroupAllItemsNoPage(groupId);
            List<GroupItemVo> groupItemVos = new ArrayList<GroupItemVo>();

            for (GroupItem item : groupItems) {
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
                if (groupItemMapper.getIsDefault(groupId) == 1) {
//            是默认分组
                    if (item.getEndTime() != 0) {
//                    有限制时间长度
//                    1  先找数据库数据有没有记录
//                    2  没有数据就直接返回null
                        Float recordPrice = groupItemMapper.getStockRecordByDay(item.getSymbol(), item.getEndTime());
                        if (recordPrice != null) {
                            nowPrice = recordPrice;
                        } else {
                            nowPrice = null;
                        }
                    } else {
//                    没有限制观察时长查看新浪接口
                        nowPrice = ApiUtils.getStockNowPriceSina(item.getSymbol());
                    }
                } else {
//            不是默认分组
                    if (item.getEndTime() != 0) {
//                    有限制时间长度
//                    1  先找数据库数据有没有记录
//                    2  没有揭露就去查接口
                        Float recordPrice = groupItemMapper.getStockRecordByDay(item.getSymbol(), item.getEndTime());
                        if (recordPrice != null) {
                            nowPrice = recordPrice;
                        } else {
                            nowPrice = ApiUtils.getStockHistoryPrice(item.getEndTime(), item.getSymbol());
                        }
                    } else {
//                    没有限制观察时长查看新浪接口
                        nowPrice = ApiUtils.getStockNowPriceSina(item.getSymbol());
                    }
                }
                vo.setNowPrice(nowPrice);
                if (nowPrice != null) {
                    vo.setProfit((nowPrice - item.getBuyPrice()) * item.getBuyNum());
                } else {
                    vo.setProfit(0.0f);
                }
                DecimalFormat decimalFormat = new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                if (item.getBuyPrice() != 0.0f) {
                    String p = "";
                    if (nowPrice != null && item.getBuyPrice() != 0) {
                        p = decimalFormat.format(((nowPrice / item.getBuyPrice()) - 1) * 100);//format 返回的是字符串
                    } else {
                        p = "0.0";
                    }
                    vo.setProfitPencent(p + "%");
                } else {
                    vo.setProfitPencent(0.0 + "%");
                }
                float p = 0.0f;
                if (nowPrice != null && item.getBuyPrice() != 0) {
                    p = ((nowPrice / item.getBuyPrice()) - 1) * 100;
                } else {
                    p = 0.0f;
                }
                vo.setProfitPencentNum(p);
//            检查在回测日期记录内涨跌平数据
                List<StockRecord> list = new ArrayList<>();
                if (item.getEndTime() == 0) {
                    list = groupItemMapper.getAllStockEndTimeNone(item.getSymbol(), new Date(item.getBuyTime()*1000));
                } else {
                    list = groupItemMapper.getAllStockEndTime(item.getSymbol(), new Date(item.getBuyTime()*1000), new Date(item.getEndTime()*1000));
                }
                int raiseDays = 0;
                int dropDays = 0;
                int blanceDays = 0;
                for (StockRecord sr : list) {
                    if (sr.getBeginPrice() > sr.getEndPrice()) {
                        dropDays += 1;
                    } else if (sr.getBeginPrice() == sr.getEndPrice()) {
                        blanceDays += 1;
                    } else {
                        raiseDays += 1;
                    }
                }
                vo.setTotalDays(list.size());
                vo.setRaiseDays(raiseDays);
                vo.setDropDays(dropDays);
                vo.setBlanceDays(blanceDays);

                groupItemVos.add(vo);
            }
            groupItemVosInsert.addAll(groupItemVos);
        }
        //        操作前删除全部数据
        timerMapper.deleteDefaultItem();
        //            批量插入defaultitem
        try {
            timerMapper.insertDefaultItem(groupItemVosInsert);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //    批量查询
    public String[] getStocksAllNowPrice(List<Stock> stocks) {
        HashMap<String, String> params = new HashMap<>();
        String stockNumStr = "";
        for (Stock stock : stocks) {
            stockNumStr += stock.getSymbol() + ",";
        }
        stockNumStr = stockNumStr.substring(0, stockNumStr.length() - 1);
        params.put("list", stockNumStr);
        String resultStr = HttpUtils.get("http://hq.sinajs.cn", params);
        String result[] = resultStr.split(";");
        return result;
    }
}
