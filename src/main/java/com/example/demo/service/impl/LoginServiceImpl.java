package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.callback.R;
import com.example.demo.domain.dto.UserRegisterDto;
import com.example.demo.domain.po.GroupItem;
import com.example.demo.domain.po.Stock;
import com.example.demo.domain.po.TestGroup;
import com.example.demo.domain.po.User;
import com.example.demo.domain.vo.LoginVo;
import com.example.demo.mapper.LoginMapper;
import com.example.demo.service.GroupItemService;
import com.example.demo.service.LoginService;
import com.example.demo.utils.HttpUtils;
import com.example.demo.utils.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*
 * @author p78o2
 * @date 2020/8/28
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private AsyncTack asyncTack;

    @Override
    public R login(User user) {
//        检查账号是否存在
        if (loginMapper.isExistAccount(user.getAccount()) > 0) {
//            账号存在
            User loginUser = loginMapper.checkAccountPwd(user.getAccount(), ToolsUtils.stringToMD5(user.getPwd()));
            if (loginUser != null) {
//                密码正确
                String token = ToolsUtils.stringToMD5(user.getAccount() + String.valueOf(new Date()) + user.getPwd());
                int result = loginMapper.updateToken(loginUser.getId(), token);
                if (result > 0) {
                    LoginVo vo = new LoginVo(loginUser.getId(), token);
                    return new R(true, R.REQUEST_SUCCESS, vo, "登陆成功");
                } else {
                    return new R(false, R.REQUEST_FAIL, null, "操作失败");
                }
            } else {
                return new R(false, R.ACCOUNT_PWD_ERROR, null, "密码错误");
            }
        } else {
//            账号不存在
            return new R(false, R.ACCOUNT_NOT_EXIST, null, "账号不存在");
        }
    }

    @Override
    public int userRegister(UserRegisterDto dto) {
        if (!dto.getPwd().equals(dto.getPwdRec())) {
//            密码与确认密码不一致
            return 0;
        } else {
//            检查账号是否重复
            if (loginMapper.checkRegister(dto.getAccount()) > 0)
                return -2;
            User user = new User();
            user.setAccount(dto.getAccount());
            user.setPwd(ToolsUtils.stringToMD5(dto.getPwd()));
            user.setCreateTime(new Date());
            loginMapper.userRegister(user);
            TestGroup testGroup = new TestGroup();
            testGroup.setUserId(user.getId());
            testGroup.setGroupName("沪深A");
            testGroup.setCreateTime(new Date());
            testGroup.setIsdefault(1);
            loginMapper.insertTestGroup(testGroup);
            asyncTack.batchLongTime(user.getId(), testGroup.getId());
            return 1;
        }
    }
}
