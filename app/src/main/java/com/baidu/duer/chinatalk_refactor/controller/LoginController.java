package com.baidu.duer.chinatalk_refactor.controller;

import android.content.Context;
import android.util.Log;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.api.LoginService;
import com.baidu.duer.chinatalk_refactor.bean.Result;
import com.baidu.duer.chinatalk_refactor.bean.user.LoginData;
import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.user.LoggedInUser;
import com.baidu.duer.chinatalk_refactor.utils.OkHttpUtil;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 登录网络请求
 */
public class LoginController {

    private static String TAG = "LoginController";

    public static Result<LoggedInUser> login(Context context, LoginData loginData) {

        // 构建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                // 设置网络请求BaseUrl地址
                .baseUrl(context.getString(R.string.baseUrl))
                .client(OkHttpUtil.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                // 设置数据解析器
                .build();
        // 创建网络请求接口对象实例
        LoginService loginService = retrofit.create(LoginService.class);
        // 对发送请求进行封装
        Call<ServiceResponse<ArrayList<LoggedInUser>>> dataCall = loginService.login(loginData);
        // 同步请求
        Response<ServiceResponse<ArrayList<LoggedInUser>>> data = null;
        try {
            data = dataCall.execute();
            Log.d(TAG, "请求成功: " + data);
            // 解析数据
            ServiceResponse<ArrayList<LoggedInUser>> body = data.body();
            if(body.getData().size() != 0) {
                LoggedInUser loggedInUser = body.getData().get(0);
                return new Result.Success<>(loggedInUser);
            } else {
                throw new IOException(body.toString());
            }
        } catch (IOException e) {
            return new Result.Error(new IOException("Error logging in", e)); // 出错返回错误信息
        }
    }
}
