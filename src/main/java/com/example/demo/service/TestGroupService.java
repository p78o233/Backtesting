package com.example.demo.service;
/*
 * @author p78o2
 * @date 2020/8/31
 */

import com.example.demo.domain.dto.BatchDeleteTestGroup;
import com.example.demo.domain.po.TestGroup;
import com.example.demo.domain.vo.TestGroupVo;

import java.util.List;

public interface TestGroupService {
//    获取全部回测分组
    public List<TestGroupVo> getAllTestGroup(int userId);
//    新增或者修改回测分组
    public int ioeTestGroup(TestGroup testGroup);
//    批量删除回测分组
    public int batchDeleteTestGroup(BatchDeleteTestGroup dto);
}
