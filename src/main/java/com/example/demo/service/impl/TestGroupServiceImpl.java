package com.example.demo.service.impl;

import com.example.demo.domain.dto.BatchDeleteTestGroup;
import com.example.demo.domain.po.TestGroup;
import com.example.demo.mapper.TestGroupMapper;
import com.example.demo.service.TestGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/*
 * @author p78o2
 * @date 2020/8/31
 */
@Service
public class TestGroupServiceImpl implements TestGroupService {
    @Autowired
    private TestGroupMapper testGroupMapper;

    @Override
    public List<TestGroup> getAllTestGroup(int userId) {
        return testGroupMapper.getAllTestGroup(userId);
    }

    @Override
    public int ioeTestGroup(TestGroup testGroup) {
        if(testGroup.getId() == null){
//            新增
            //        检查是否有重名的回测分组
            if(testGroupMapper.checkGroupNameExist(testGroup.getGroupName(),testGroup.getUserId())>0){
                return -1;
            }
            testGroup.setCreateTime(new Date());
            if(testGroupMapper.insertTestGroup(testGroup)>0)
                return 1;
            return 0;
        }else{
//            修改
            //        检查是否有重名的回测分组
            if(testGroupMapper.checkGroupNameExistEdit(testGroup.getGroupName(),testGroup.getUserId(),testGroup.getId())>0){
                return -1;
            }
            testGroup.setModifyTime(new Date());
            if(testGroupMapper.updateTestGroup(testGroup)>0)
                return 1;
            return 0;
        }
    }

    @Override
    public int batchDeleteTestGroup(BatchDeleteTestGroup dto) {
//        检查删除的分组是否默认分组
        for(int i = 0;i<dto.getIds().size();i++){
            if(testGroupMapper.getIsDefault(dto.getIds().get(i)) == 1){
                dto.getIds().remove(i);
                break;
            }
        }
        if(testGroupMapper.batchDeleteTestGroup(dto.getUserId(),dto.getIds())>0) {
            testGroupMapper.batchDeleteAllGroupItem(dto.getUserId(),dto.getIds());
            return 1;
        }
        return 0;
    }
}
