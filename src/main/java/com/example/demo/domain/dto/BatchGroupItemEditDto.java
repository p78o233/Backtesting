package com.example.demo.domain.dto;

import java.util.List;

/*
 * @author p78o2
 * @date 2020/9/1
 */
//批量操作回测分组内数据
public class BatchGroupItemEditDto {
//    回测分组id
    private int groupId;
//    批量操作的itemid
    private List<Integer> itemIds;
//    开始回测时间
    private Long beginTime;
//    结束回测时间
    private Long endTime;

    @Override
    public String toString() {
        return "BatchGroupItemEditDto{" +
                "groupId=" + groupId +
                ", itemIds=" + itemIds +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }

    public BatchGroupItemEditDto() {
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public BatchGroupItemEditDto(int groupId, List<Integer> itemIds, Long beginTime, Long endTime) {
        this.groupId = groupId;
        this.itemIds = itemIds;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
