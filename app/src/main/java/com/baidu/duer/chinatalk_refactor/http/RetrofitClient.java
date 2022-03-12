package com.baidu.duer.chinatalk_refactor.http;

import androidx.annotation.AnyRes;

import com.baidu.duer.chinatalk_refactor.SampleApplicationLike;
import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.utils.OkHttpUtil;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit的建造者模式封装
 */
public class RetrofitClient {
    /**
     *baseUrl
     */
    private final String baseUrl;
    /**
     * json解析器
     */
    private final Converter.Factory converter;
    /**
     * okhttpClient
     */
    private final OkHttpClient client;
    /**
     * 异步支持
     */
    private final CallAdapter.Factory callAdapter;

    /**
     * 其他属性可随时扩展
     */
//    private final Any any;

    private RetrofitClient(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.converter = builder.converter;
        this.client = builder.client;
        this.callAdapter = builder.callAdapter;
//        this.any = builder.any;
    }
    private Retrofit build() {
        return new Retrofit.Builder()
                .baseUrl(this.baseUrl)
                .client(this.client)
                .addConverterFactory(this.converter)
                .addCallAdapterFactory(this.callAdapter)
                .build();
    }

    public static class Builder {

        private final String baseUrl;
        private final Converter.Factory converter;
        private final OkHttpClient client;
        private final CallAdapter.Factory callAdapter;
//        private Any any;

        public Builder() {
            this.baseUrl = SampleApplicationLike.getContext().getString(R.string.baseUrl);
            this.converter = GsonConverterFactory.create();
            this.client = OkHttpUtil.getOkHttpClient();
            this.callAdapter = RxJava2CallAdapterFactory.create();
        }

        public Builder(String baseUrl) {
            this.baseUrl = baseUrl;
            this.converter = GsonConverterFactory.create();
            this.client = OkHttpUtil.getOkHttpClient();
            this.callAdapter = RxJava2CallAdapterFactory.create();
        }

        public Builder(String baseUrl, Converter.Factory converter, OkHttpClient client) {
            this.baseUrl = baseUrl;
            this.converter = converter;
            this.client = client;
            this.callAdapter = RxJava2CallAdapterFactory.create();
        }

//        public Builder any(Any any) {
//            this.any = any;
//            return this;
//        }

        public Retrofit build() {
            return new RetrofitClient(this).build();
        }
    }
}

/**
 * 使用
 */
//Retrofit retrofit = new RetrofitClient.Builder().any(Any).build();
