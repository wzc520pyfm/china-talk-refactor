package com.baidu.duer.chinatalk_refactor.api;

import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.record.GettedWrongQuestion;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WrongQuestionService {
    /**
     * 查询当前用户所有错题
     */
    @GET("records/wrong/list")
    Observable<ServiceResponse<GettedWrongQuestion>> getWrongQuestions();
}
