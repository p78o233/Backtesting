package com.example.demo.domain.dto;

import java.util.List;

/*
 * @author p78o2
 * @date 2020/8/31
 */
//批量删除用户回测分组dto
public class BatchDeleteTestGroup {
//    用户id
    private int userId;
//    分组id数组
    private List<Integer> ids;

    public BatchDeleteTestGroup() {
    }

    @Override
    public String toString() {
        return "BatchDeleteTestGroup{" +
                "userId=" + userId +
                ", ids=" + ids +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public BatchDeleteTestGroup(int userId, List<Integer> ids) {
        this.userId = userId;
        this.ids = ids;
    }
}
