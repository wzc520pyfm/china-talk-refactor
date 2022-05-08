package com.baidu.duer.chinatalk_refactor.bean.user;

import androidx.annotation.NonNull;

/**
 * 登录表单信息
 */
public class LoginData {
    @NonNull
    private String phone;
    @NonNull
    private String password;
    @NonNull
    private String role;

    public LoginData(@NonNull String phone, @NonNull String password) {
        this.phone = phone;
        this.password = password;
        this.role = "Student";
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
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
                "user_phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", user_role=" + phone +
                '}';
    }
}
