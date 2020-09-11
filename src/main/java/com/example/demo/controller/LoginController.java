package com.example.demo.controller;

import com.example.demo.callback.R;
import com.example.demo.domain.dto.UserRegisterDto;
import com.example.demo.domain.po.User;
import com.example.demo.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author p78o2
 * @date 2020/8/31
 */
@RestController
@RequestMapping(value = "/stock")
public class LoginController {
    @Autowired
    private LoginService loginService;
    private static Logger log = LogManager.getLogger(LoginController.class);
    @PostMapping(value = "/login")
    public R login(@RequestBody User user){
        log.info("登陆接口user："+user.toString());
        return loginService.login(user);
    }

    @PostMapping(value = "/userRegister")
    public R userRegister(@RequestBody UserRegisterDto dto){
        log.info("注册接口UserRegisterDto："+dto.toString());
        int result = loginService.userRegister(dto);
        if(result == 0){
            return new R(false,R.ACCOUNT_PWD_EQUALS,null,"密码与确认密码不一致");
        }else if(result == -1){
            return new R(false,R.USER_REGISTER_FAIL,null,"用户创建失败");
        } else if(result == -2){
            return new R(false,R.USER_REGISTER_FAIL,null,"账号重复，创建失败");
        }
        else {
            return new R(true,R.REQUEST_SUCCESS,null,"用户创建成功");
        }
    }
}
