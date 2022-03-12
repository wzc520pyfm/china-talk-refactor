package com.baidu.duer.chinatalk_refactor.ui.login;

import androidx.annotation.Nullable;


/**
 * Authentication result : success (user details) or error message.认证结果:成功(用户详细信息)或错误信息.
 */
class LoginResult {
    @Nullable
    private LoggedInUserView success; // 要向UI公开的数据
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "success=" + success +
                ", error=" + error +
                '}';
    }
}