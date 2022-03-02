package com.baidu.duer.chinatalk_refactor.ui.login;

/**
 * Class exposing authenticated user details to the UI.此类将经过身份验证的用户详细信息公开给UI。
 */
class LoggedInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI 其他需要向UI公开的数据

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}