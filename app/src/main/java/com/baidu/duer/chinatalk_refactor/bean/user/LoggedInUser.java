package com.baidu.duer.chinatalk_refactor.bean.user;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 * 数据类,用于捕获已登录用户的用户信息
 */
public class LoggedInUser {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoggedInUser{" +
                "token='" + token + '\'' +
                '}';
    }
}