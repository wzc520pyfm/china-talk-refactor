package com.baidu.duer.chinatalk_refactor.api;

import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.question.GettedQuestion;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 试题相关接口
 */
public interface QuestionService {
    /**
     * 查询指定id的题目
     */
    @GET("questions/{id}")
    Observable<ServiceResponse<GettedQuestion>> getOneQuestion(@Path("id") int id);
}
