package com.example.demo.domain.po;

import java.util.Date;

/*
 * @author p78o2
 * @date 2020/9/1
 */
//分组内数据
public class GroupItem {
//    主键
    private Integer id;
//    购买时间
    private Long buyTime;
//    购买数量
    private int buyNum;
//    购买价格
    private float buyPrice;
//    股票编号
    private String symbol;
//    股票名
    private String sname;
//    分组id
    private int groupId;
//    是否删除
    private int isdel;
//    船舰时间
    private Date createTime;
//    修改时间
    private Date modifyTime;
//    用户id
    private int userId;
//    回测结束时间，实时的就填0
    private Long endTime;

    public GroupItem() {
    }

    @Override
    public String toString() {
        return "GroupItem{" +
                "id=" + id +
                ", buyTime=" + buyTime +
                ", buyNum=" + buyNum +
                ", buyPrice=" + buyPrice +
                ", symbol='" + symbol + '\'' +
                ", sname='" + sname + '\'' +
                ", groupId=" + groupId +
                ", isdel=" + isdel +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", userId=" + userId +
                ", endTime=" + endTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public GroupItem(Integer id, Long buyTime, int buyNum, float buyPrice, String symbol, String sname, int groupId, int isdel, Date createTime, Date modifyTime, int userId, Long endTime) {
        this.id = id;
        this.buyTime = buyTime;
        this.buyNum = buyNum;
        this.buyPrice = buyPrice;
        this.symbol = symbol;
        this.sname = sname;
        this.groupId = groupId;
        this.isdel = isdel;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.userId = userId;
        this.endTime = endTime;
    }
}
