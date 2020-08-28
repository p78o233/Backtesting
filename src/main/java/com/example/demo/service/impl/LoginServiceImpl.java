package com.example.demo.service.impl;

import com.example.demo.callback.R;
import com.example.demo.domain.po.User;
import com.example.demo.mapper.LoginMapper;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author p78o2
 * @date 2020/8/28
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Override
    public R login(User user) {

        return null;
    }

    @Override
    public int userRegister(User user) {
        return 0;
    }
}
