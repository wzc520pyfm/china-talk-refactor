package com.baidu.duer.chinatalk_refactor.data.login;

import com.baidu.duer.chinatalk_refactor.SampleApplicationLike;
import com.baidu.duer.chinatalk_refactor.bean.user.LoginData;
import com.baidu.duer.chinatalk_refactor.bean.Result;
import com.baidu.duer.chinatalk_refactor.controller.LoginController;
import com.baidu.duer.chinatalk_refactor.bean.user.LoggedInUser;
import com.baidu.duer.chinatalk_refactor.utils.SharedUtil;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 * 类,用于从远程数据源请求身份验证和用户信息,并维护登录状态和保存用户凭据信息.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private SharedUtil sharedUtil = SharedUtil.getInstance();;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // 如果用户凭据将缓存到本地存储中，建议对其进行加密
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository() {
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
    }

    private void setLoggedInUser(LoggedInUser user) { // 存储user信息到类中
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // 如果用户凭据将缓存到本地存储中，建议对其进行加密
        // @see https://developer.android.com/training/articles/keystore
        sharedUtil.writeShared("token", "Bearer " + user.getToken()); // 存储token
    }

    public Result<LoggedInUser> login(LoginData loginData) {
        // handle login. 处理登录逻辑
        Result<LoggedInUser> result = LoginController.login(SampleApplicationLike.getContext(),loginData);// 取得登录结果信息

        if (result instanceof Result.Success) { // 如果登录结果是success
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData()); // 取出结果信息并存储
        }
        return result; // 返回登录结果信息
    }
}