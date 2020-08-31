package com.example.demo.domain.dto;
/*
 * @author p78o2
 * @date 2020/8/31
 */
//用户注册dto
public class UserRegisterDto {
    public String account;
    public String pwd;
    public String pwdRec;

    @Override
    public String toString() {
        return "UserRegisterDto{" +
                "account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", pwdRec='" + pwdRec + '\'' +
                '}';
    }

    public UserRegisterDto() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwdRec() {
        return pwdRec;
    }

    public void setPwdRec(String pwdRec) {
        this.pwdRec = pwdRec;
    }

    public UserRegisterDto(String account, String pwd, String pwdRec) {
        this.account = account;
        this.pwd = pwd;
        this.pwdRec = pwdRec;
    }
}
