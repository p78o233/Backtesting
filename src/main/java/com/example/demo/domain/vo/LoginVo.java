package com.example.demo.domain.vo;
/*
 * @author p78o2
 * @date 2020/9/7
 */

public class LoginVo {
    private int userId;
    private String token;

    @Override
    public String toString() {
        return "LoginVo{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                '}';
    }

    public LoginVo() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginVo(int userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
