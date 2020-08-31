package com.example.demo.domain.po;

import java.util.Date;

/*
 * @author p78o2
 * @date 2020/8/31
 */
//股票记录表
public class StockRecord {
//    主键
    private Integer id;
//    股票编号
    private String symbol;
//    股票名称
    private String sname;
//    开始时间
    private float beginPrice;
//    收盘时间
    private float endPrice;
//    最高价
    private float highPrice;
//    最低价
    private float lowPrice;
//    记录时间
    private Date recordTime;

    public StockRecord() {
    }

    @Override
    public String toString() {
        return "StockRecord{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", sname='" + sname + '\'' +
                ", beginPrice=" + beginPrice +
                ", endPrice=" + endPrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", recordTime=" + recordTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public float getBeginPrice() {
        return beginPrice;
    }

    public void setBeginPrice(float beginPrice) {
        this.beginPrice = beginPrice;
    }

    public float getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(float endPrice) {
        this.endPrice = endPrice;
    }

    public float getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(float highPrice) {
        this.highPrice = highPrice;
    }

    public float getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(float lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public StockRecord(Integer id, String symbol, String sname, float beginPrice, float endPrice, float highPrice, float lowPrice, Date recordTime) {
        this.id = id;
        this.symbol = symbol;
        this.sname = sname;
        this.beginPrice = beginPrice;
        this.endPrice = endPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.recordTime = recordTime;
    }
}
