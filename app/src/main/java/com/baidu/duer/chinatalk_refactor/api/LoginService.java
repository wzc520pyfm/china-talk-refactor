package com.baidu.duer.chinatalk_refactor.api;

import com.baidu.duer.chinatalk_refactor.bean.user.LoginData;
import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.user.LoggedInUser;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 登录接口
 */
public interface LoginService {
    @POST("user/login")
    Observable<ServiceResponse<ArrayList<LoggedInUser>>> login(@Body LoginData loginData);
}
