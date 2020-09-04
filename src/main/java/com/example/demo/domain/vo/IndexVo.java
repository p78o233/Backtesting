package com.example.demo.domain.vo;
/*
 * @author p78o2
 * @date 2020/9/3
 */
//首页顶部数据
public class IndexVo {
//    总盈亏百分比
    private double profitPercentage;
//    总盈亏数字
    private double profitValue;
//    总市值
    private double totalMarketValue;
//    总成本
    private double totalCost;
//    当日盈亏百分比
    private double dayProfitPercentage;
//    当日盈亏数字
    private double dayProfitValue;

    public IndexVo() {
    }

    @Override
    public String toString() {
        return "IndexVo{" +
                "profitPercentage=" + profitPercentage +
                ", profitValue=" + profitValue +
                ", totalMarketValue=" + totalMarketValue +
                ", totalCost=" + totalCost +
                ", dayProfitPercentage=" + dayProfitPercentage +
                ", dayProfitValue=" + dayProfitValue +
                '}';
    }

    public double getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(double profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public double getProfitValue() {
        return profitValue;
    }

    public void setProfitValue(double profitValue) {
        this.profitValue = profitValue;
    }

    public double getTotalMarketValue() {
        return totalMarketValue;
    }

    public void setTotalMarketValue(double totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getDayProfitPercentage() {
        return dayProfitPercentage;
    }

    public void setDayProfitPercentage(double dayProfitPercentage) {
        this.dayProfitPercentage = dayProfitPercentage;
    }

    public double getDayProfitValue() {
        return dayProfitValue;
    }

    public void setDayProfitValue(double dayProfitValue) {
        this.dayProfitValue = dayProfitValue;
    }

    public IndexVo(double profitPercentage, double profitValue, double totalMarketValue, double totalCost, double dayProfitPercentage, double dayProfitValue) {
        this.profitPercentage = profitPercentage;
        this.profitValue = profitValue;
        this.totalMarketValue = totalMarketValue;
        this.totalCost = totalCost;
        this.dayProfitPercentage = dayProfitPercentage;
        this.dayProfitValue = dayProfitValue;
    }
}
