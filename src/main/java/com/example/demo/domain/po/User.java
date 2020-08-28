package com.example.demo.domain.po;

import java.util.Date;

/*
 * @author p78o2
 * @date 2020/8/28
 */
//用户表
public class User {
//    主键
    private Integer id;
//    账号
    private String account;
//    密码
    private String pwd;
//    创建时间
    private Date createTime;
//    修改时间
    private Date modifyTime;
//    是否删除 0 正常 1 删除
    private int isdel;
//    令牌
    private String token;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", isdel=" + isdel +
                ", token='" + token + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User() {
    }

    public User(Integer id, String account, String pwd, Date createTime, Date modifyTime, int isdel, String token) {
        this.id = id;
        this.account = account;
        this.pwd = pwd;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.isdel = isdel;
        this.token = token;
    }
}
