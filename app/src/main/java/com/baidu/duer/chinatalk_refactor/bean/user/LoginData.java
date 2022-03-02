package com.baidu.duer.chinatalk_refactor.bean.user;

import androidx.annotation.NonNull;

/**
 * 登录信息
 */
public class LoginData {
    @NonNull
    private String user_phone;
    @NonNull
    private String password;
    @NonNull
    private Integer user_role;

    public LoginData(@NonNull String user_phone, @NonNull String password) {
        this.user_phone = user_phone;
        this.password = password;
        this.user_role = 1;
    }

    @NonNull
    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(@NonNull String user_phone) {
        this.user_phone = user_phone;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginForm{" +
                "user_phone='" + user_phone + '\'' +
                ", password='" + password + '\'' +
                ", user_role=" + user_role +
                '}';
    }
}
