package com.baidu.duer.chinatalk_refactor.http;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

//public class TokenAuthenticator implements Authenticator {
//    /**
//     * Token过期后调登录接口自动刷新
//     * 若自动刷新失败，在Error同意处理并跳转到登录界面
//     *
//     * @param route
//     * @param response
//     * @return
//     * @throws IOException
//     */
//    @Nullable
//    @Override
//    public Request authenticate(@Nullable Route route, Response response) throws IOException {
//        int code = response.code();
//        if (code == HttpCode.REQUEST_TOKEN_INVALID) {
//            String account = Utils.getAccount(); // 获取账号
//            String encryptPassword = Utils.getEncryptPassword(); // 获取密码
//            if (Utils.isNonEmpty(account) && Utils.isNonEmpty(encryptPassword)) { // 判断非空
//                HttpApi httpApi = RetrofitFactory.createRetrofit(false).create(HttpApi.class);//注意：刷新Token不能再拦截，否则就会陷入无限循环
//                //同步刷新Token
//                Call<SeengeneResponse<LoginResponseBody>> responseCall = httpApi.requestToken(new LoginRequestBody(account, encryptPassword));
//                retrofit2.Response<SeengeneResponse<LoginResponseBody>> execute = responseCall.execute();
//                if (!execute.isSuccessful()) {
//                    return null;
//                }
//                SeengeneResponse<LoginResponseBody> body = execute.body();
//                if (body != null) {
//                    LoginResponseBody data = body.getData();
//                    if (data != null) {
//                        String token = data.getToken();
//                        //保存Token
//                        Utils.saveLoginToken(token);
//                        return response.request().newBuilder()
//                                .header(Config.HTTP_TOKEN_KET, token)
//                                .build();
//                    }
//                }
//            }
//        }
//        return response.request();
//    }
//}
