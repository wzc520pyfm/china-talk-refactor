package com.baidu.duer.chinatalk_refactor.utils;

//import com.baidu.duer.chinatalk_refactor.http.TokenAuthenticator;
import com.baidu.duer.chinatalk_refactor.http.TokenInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpUtil {
    public static OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(3000, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(3000, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(3000, TimeUnit.SECONDS);
        //使用拦截器
        httpClientBuilder
//                .authenticator(new TokenAuthenticator()) // token自动刷新
                .addInterceptor(new TokenInterceptor());

        return httpClientBuilder.build();
    }
}
