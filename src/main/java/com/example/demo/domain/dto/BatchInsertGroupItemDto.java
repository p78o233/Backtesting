package com.example.demo.domain.dto;

import java.util.List;

/*
 * @author p78o2
 * @date 2020/9/1
 */
//回测分组内数据批量插入
public class BatchInsertGroupItemDto {
//    分组id
    public int groupId;
//    用户id
    public int userId;
//    股票编号列表
    public List<String> symbol;
//    买入手数
    public int buyNum;
//    开始时间
    public Long buyTime;
//    结束时间
    public Long endTime;

    @Override
    public String toString() {
        return "BatchInsertGroupItemDto{" +
                "groupId=" + groupId +
                ", userId=" + userId +
                ", symbol=" + symbol +
                ", buyNum=" + buyNum +
                ", buyTime=" + buyTime +
                ", endTime=" + endTime +
                '}';
    }

    public BatchInsertGroupItemDto() {
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getSymbol() {
        return symbol;
    }

    public void setSymbol(List<String> symbol) {
        this.symbol = symbol;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public BatchInsertGroupItemDto(int groupId, int userId, List<String> symbol, int buyNum, Long buyTime, Long endTime) {
        this.groupId = groupId;
        this.userId = userId;
        this.symbol = symbol;
        this.buyNum = buyNum;
        this.buyTime = buyTime;
        this.endTime = endTime;
    }
}
