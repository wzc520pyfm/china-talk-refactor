package com.baidu.duer.chinatalk_refactor.api;

import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.exam.GettedExam;
import com.baidu.duer.chinatalk_refactor.bean.exam.GettedExams;
import com.baidu.duer.chinatalk_refactor.bean.exam.UserAnswerResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 试卷相关接口
 */
public interface ExamPaperService {
    /**
     * 查询所有HSK模拟试卷并且携带当前用户答题记录的最高分
     */
    @GET("papers/hskmocks/with-user-score/all")
    Observable<ServiceResponse<GettedExams>> getAllHskMocksWithUserScoreAll();

    /**
     * 查询指定的一张试卷信息
     */
    @GET("papers/paper/{id}")
    Observable<ServiceResponse<GettedExam>> getOnePaper(@Path("id") int id);

    /**
     * 提交成绩
     */
    @POST("records/score")
    Observable<ServiceResponse> submitScore(@Body UserAnswerResult userAnswerResult);
}
