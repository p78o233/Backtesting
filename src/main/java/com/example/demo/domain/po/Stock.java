package com.example.demo.domain.po;
/*
 * @author p78o2
 * @date 2020/8/28
 */
//股票表
public class Stock {
//    主键
    private Integer id;
//    接口返回的股票id
    private String stoid;
//    股票编号
    private String symbol;
//    股票名称
    private String sname;

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", stoid='" + stoid + '\'' +
                ", symbol='" + symbol + '\'' +
                ", sname='" + sname + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoid() {
        return stoid;
    }

    public void setStoid(String stoid) {
        this.stoid = stoid;
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

    public Stock() {
    }

    public Stock(Integer id, String stoid, String symbol, String sname) {
        this.id = id;
        this.stoid = stoid;
        this.symbol = symbol;
        this.sname = sname;
    }
}
