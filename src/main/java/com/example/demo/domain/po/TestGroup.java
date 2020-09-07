package com.example.demo.domain.po;

import java.util.Date;

/*
 * @author p78o2
 * @date 2020/8/28
 */
//回测分组表
public class TestGroup {
//    主键
    private Integer id;
//    用户id
    private int userId;
//    分组名
    private String groupName;
    //    创建时间
    private Date createTime;
    //    修改时间
    private Date modifyTime;
    //    是否删除 0 正常 1 删除
    private int isdel;
//    是否默认分组1是 0不是
    private int isdefault;

    @Override
    public String toString() {
        return "TestGroup{" +
                "id=" + id +
                ", userId=" + userId +
                ", groupName='" + groupName + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", isdel=" + isdel +
                ", isdefault=" + isdefault +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }

    public TestGroup(Integer id, int userId, String groupName, Date createTime, Date modifyTime, int isdel, int isdefault) {
        this.id = id;
        this.userId = userId;
        this.groupName = groupName;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.isdel = isdel;
        this.isdefault = isdefault;
    }

    public TestGroup() {
    }
}
