package com.baidu.duer.chinatalk_refactor.api;

import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.question.GettedQuestion;
import com.baidu.duer.chinatalk_refactor.bean.question.GettedQuestions;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 试题相关接口
 */
public interface QuestionService {
    /**
     * 查询指定id的题目
     */
    @GET("questions/{id}")
    Observable<ServiceResponse<GettedQuestion>> getOneQuestion(@Path("id") int id);

    /**
     * 查询叙述题
     */
    @GET("questions/narrates")
    Observable<ServiceResponse<GettedQuestions>> getNarrateQuestions(@Query("questionClassification") String questionClassification, @Query("questionDifficulty") String questionDifficulty);
}
