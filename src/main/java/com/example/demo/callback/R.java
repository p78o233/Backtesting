package com.example.demo.callback;/*
 * @author p78o2
 * @date 2019/9/11
 */

public class R {
    private boolean ret;
    private int state;
    private Object data;
    private String message;
//    操作正常
    public static int REQUEST_SUCCESS = 200;
//    部分数据操作失败
    public static int SOME_ERROR = 201;
//    账号不存在
    public static int ACCOUNT_NOT_EXIST = 301;
//    账号密码错误
    public static int ACCOUNT_PWD_ERROR = 302;
//    注册时密码和确认密码不一致
    public static int ACCOUNT_PWD_EQUALS = 303;
//    注册失败
    public static int USER_REGISTER_FAIL = 304;
//    操作失败
    public static int REQUEST_FAIL = 500;
    public R() {
    }

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public R(boolean ret, int state, Object data, String message) {
        this.ret = ret;
        this.state = state;
        this.data = data;
        this.message = message;
    }

    @Override
    public String toString() {
        return "R{" +
                "ret=" + ret +
                ", state=" + state +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
