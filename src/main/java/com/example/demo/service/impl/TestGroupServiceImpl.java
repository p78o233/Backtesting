package com.example.demo.service.impl;

import com.example.demo.domain.dto.BatchDeleteTestGroup;
import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.TestGroup;
import com.example.demo.domain.vo.TestGroupVo;
import com.example.demo.mapper.TestGroupMapper;
import com.example.demo.service.TestGroupService;
import com.example.demo.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
    public List<TestGroupVo> getAllTestGroup(int userId) {
        List<TestGroup> groups = new ArrayList<>();
        groups = testGroupMapper.getAllTestGroup(userId);
        List<TestGroupVo> vos = new ArrayList<>();
        for (TestGroup t : groups) {
            TestGroupVo vo = new TestGroupVo();
//            获取不是默认分组的分组内全部数
//                获取全部的groupitem有效的数据
            List<GroupItem> items = new ArrayList<>();
            items = testGroupMapper.groupItems(t.getId());
            float sumBuyPrice = 0.0f;
            float sumMarketPrice = 0.0f;
            if (t.getIsdefault() == 0) {
                for (GroupItem item : items) {
                    sumBuyPrice += item.getBuyPrice();
                    if (item.getEndTime() != 0) {
                        sumMarketPrice += ApiUtils.getStockHistoryPrice(item.getEndTime(), item.getSymbol());
                    } else {
                        sumMarketPrice += ApiUtils.getStockNowPriceSina(item.getSymbol());
                    }
                }
                DecimalFormat decimalFormat = new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                String p = decimalFormat.format(((sumMarketPrice / sumBuyPrice) - 1) * 100);
                vo = new TestGroupVo(t.getId(), t.getUserId(), t.getGroupName(), t.getCreateTime(), t.getModifyTime(), t.getIsdel(), t.getIsdefault(), p);
            }
            else{
                vo = new TestGroupVo(t.getId(), t.getUserId(), t.getGroupName(), t.getCreateTime(), t.getModifyTime(), t.getIsdel(), t.getIsdefault(), "0.00");
            }
           vos.add(vo);
        }
        return vos;
    }

    @Override
    public int ioeTestGroup(TestGroup testGroup) {
        if (testGroup.getId() == null) {
//            新增
            //        检查是否有重名的回测分组
            if (testGroupMapper.checkGroupNameExist(testGroup.getGroupName(), testGroup.getUserId()) > 0) {
                return -1;
            }
            testGroup.setCreateTime(new Date());
            if (testGroupMapper.insertTestGroup(testGroup) > 0)
                return 1;
            return 0;
        } else {
//            修改
            //        检查是否有重名的回测分组
            if (testGroupMapper.checkGroupNameExistEdit(testGroup.getGroupName(), testGroup.getUserId(), testGroup.getId()) > 0) {
                return -1;
            }
            testGroup.setModifyTime(new Date());
            if (testGroupMapper.updateTestGroup(testGroup) > 0)
                return 1;
            return 0;
        }
    }

    @Override
    public int batchDeleteTestGroup(BatchDeleteTestGroup dto) {
//        检查删除的分组是否默认分组
        for (int i = 0; i < dto.getIds().size(); i++) {
            if (testGroupMapper.getIsDefault(dto.getIds().get(i)) == 1) {
                dto.getIds().remove(i);
                break;
            }
        }
        if (testGroupMapper.batchDeleteTestGroup(dto.getUserId(), dto.getIds()) > 0) {
            testGroupMapper.batchDeleteAllGroupItem(dto.getUserId(), dto.getIds());
            return 1;
        }
        return 0;
    }
}
