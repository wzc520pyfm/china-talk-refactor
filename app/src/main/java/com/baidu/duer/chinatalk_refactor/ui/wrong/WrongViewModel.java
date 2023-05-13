package com.baidu.duer.chinatalk_refactor.ui.wrong;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.api.ExamPaperService;
import com.baidu.duer.chinatalk_refactor.api.WrongQuestionService;
import com.baidu.duer.chinatalk_refactor.bean.Result;
import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.exam.GettedExam;
import com.baidu.duer.chinatalk_refactor.bean.record.GettedWrongQuestion;
import com.baidu.duer.chinatalk_refactor.http.RetrofitClient;
import com.baidu.duer.chinatalk_refactor.ui.real.ExamResult;
import com.baidu.duer.chinatalk_refactor.ui.real.GettedInExamView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WrongViewModel extends AndroidViewModel {
    private MutableLiveData<WrongQuestionResult> wrongQuestionResult;

    public WrongViewModel(@NonNull Application application) {
        super(application);
        wrongQuestionResult = new MutableLiveData<>();
        initWrongQuestions();
    }

    private void initWrongQuestions() {
        Observable<ServiceResponse<GettedWrongQuestion>> observable = new RetrofitClient.Builder().build().create(WrongQuestionService.class).getWrongQuestions();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceResponse<GettedWrongQuestion>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ServiceResponse<GettedWrongQuestion> gettedExamServiceResponse) {
                        Result<GettedWrongQuestion> gettedWrongQuestion = new Result.Success<>(gettedExamServiceResponse.getData());
                        if (gettedWrongQuestion instanceof Result.Success) {
                            GettedWrongQuestion data = ((Result.Success<GettedWrongQuestion>) gettedWrongQuestion).getData();
                            wrongQuestionResult.setValue(new WrongQuestionResult(new GettedInWrongView(data.getWrong())));
                        } else {
                            wrongQuestionResult.setValue(new WrongQuestionResult(R.string.failed));
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

    public LiveData<WrongQuestionResult> getWrongQuestionResult() {
        return wrongQuestionResult;
    }
}
