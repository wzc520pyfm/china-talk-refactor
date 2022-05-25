package com.baidu.duer.chinatalk_refactor.ui.real;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.SampleApplicationLike;
import com.baidu.duer.chinatalk_refactor.api.ExamPaperService;
import com.baidu.duer.chinatalk_refactor.bean.Result;
import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.exam.Exam;
import com.baidu.duer.chinatalk_refactor.bean.exam.GettedExam;
import com.baidu.duer.chinatalk_refactor.bean.exam.Score;
import com.baidu.duer.chinatalk_refactor.bean.exam.UserAnswerResult;
import com.baidu.duer.chinatalk_refactor.http.RetrofitClient;
import com.baidu.duer.chinatalk_refactor.ui.exam.ExamFragment;
import com.chenenyu.router.Router;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RealViewModel extends AndroidViewModel{
    private MutableLiveData<ExamResult> examResult;
    // 用户最终答题结果
    private MutableLiveData<UserAnswerResult> userAnswerResult;
    private Integer paperId;
    // 用户的回答
    private HashMap<Integer, String> userAnswer = new HashMap<>();
    // 用户得分
    private Integer totalScore = 0;
    // 记录用户的错题
    private HashMap<Integer, String> userWrong = new HashMap<>();
    // 提交成绩结果
    private MutableLiveData<ServiceResponse> submitResult;

    public RealViewModel(@NonNull Application application, Integer paperId) {
        super(application);
        this.paperId = paperId;
        examResult = new MutableLiveData<>();
        userAnswerResult = new MutableLiveData<>();
        submitResult = new MutableLiveData<>();
        initExam();
    }

    private void initExam() {
        Observable<ServiceResponse<GettedExam>> observable = new RetrofitClient.Builder().build().create(ExamPaperService.class).getOnePaper(paperId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceResponse<GettedExam>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ServiceResponse<GettedExam> gettedExamServiceResponse) {
                        Result<GettedExam> gettedExam = new Result.Success<>(gettedExamServiceResponse.getData());
                        if (gettedExam instanceof Result.Success) {
                            GettedExam data = ((Result.Success<GettedExam>) gettedExam).getData();
                            examResult.setValue(new ExamResult(new GettedInExamView(data.getPaper())));
                        } else {
                            examResult.setValue(new ExamResult(R.string.failed));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<ExamResult> getExamResult() {
        return examResult;
    }

    /**
     * 记录用户回答
     * @param index 题号
     * @param answer 用户答案
     * @param fag 是否答对
     */
    public void setUserAnswer(Integer index, Integer questionId, String answer, Boolean fag) {
        if(!userAnswer.containsKey(questionId)) {
            userAnswer.put(questionId, answer);
            if(fag) {
                totalScore += examResult.getValue().getSuccess().getExam().getScorePapers().get(index).getScore();
            }
        } else {
            userAnswer.replace(questionId, answer);
            if(!fag) {
                totalScore -= examResult.getValue().getSuccess().getExam().getScorePapers().get(index).getScore();
            }
        }
    }

    /**
     * 判断用户是否答完题目
     */
    public Boolean isAnswered() {
        return userAnswer.size() == examResult.getValue().getSuccess().getExam().getTotal();
    }

    /**
     * 计算用户得分
     */
    public void computeScore() {
        // 试卷
        Exam exam = examResult.getValue().getSuccess().getExam();
        userAnswerResult.setValue(new UserAnswerResult(exam.getId(), exam.getTotalScore(), totalScore));
    }

    /**
     * 提交用户成绩
     */
    public void submitScore() {
        Observable<ServiceResponse> observable = new RetrofitClient.Builder().build().create(ExamPaperService.class).submitScore(userAnswerResult.getValue());
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ServiceResponse serviceResponse) {
                        submitResult.setValue(serviceResponse);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<UserAnswerResult> getUserAnswerResult() {
        return userAnswerResult;
    }

    public LiveData<ServiceResponse> getSubmitResult() {
        return submitResult;
    }
}
