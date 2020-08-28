package com.example.demo.service;
/*
 * @author p78o2
 * @date 2020/8/28
 */

import com.example.demo.callback.R;
import com.example.demo.domain.po.User;

public interface LoginService {
//    登陆
    public R login(User user);
//    注册
    public int userRegister(User user);
}
