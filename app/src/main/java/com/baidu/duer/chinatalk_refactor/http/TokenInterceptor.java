package com.baidu.duer.chinatalk_refactor.http;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.SampleApplicationLike;
import com.baidu.duer.chinatalk_refactor.utils.SharedUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                // 从本地读取token并添加到请求头(注意: 这里本地存储的token是经过处理的)
                .addHeader("Authorization", SharedUtil.getInstance().readShared("token",""))
                .addHeader("fapp", SampleApplicationLike.getContext().getString(R.string.fapp))
                .build();
        return chain.proceed(request);
    }
}
