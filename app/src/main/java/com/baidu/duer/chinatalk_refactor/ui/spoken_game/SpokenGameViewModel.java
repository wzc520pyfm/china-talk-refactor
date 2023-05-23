package com.baidu.duer.chinatalk_refactor.ui.spoken_game;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.api.QuestionService;
import com.baidu.duer.chinatalk_refactor.bean.Result;
import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.question.GettedInQuestionsView;
import com.baidu.duer.chinatalk_refactor.bean.question.GettedQuestion;
import com.baidu.duer.chinatalk_refactor.bean.question.GettedQuestions;
import com.baidu.duer.chinatalk_refactor.bean.question.QuestionsResult;
import com.baidu.duer.chinatalk_refactor.bean.record.GettedWrongQuestion;
import com.baidu.duer.chinatalk_refactor.http.RetrofitClient;
import com.baidu.duer.chinatalk_refactor.bean.question.GettedInQuestionView;
import com.baidu.duer.chinatalk_refactor.bean.question.QuestionResult;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SpokenGameViewModel extends AndroidViewModel {
    private MutableLiveData<QuestionsResult> questionsResult;

    public SpokenGameViewModel(@NonNull Application application) {
        super(application);
        questionsResult = new MutableLiveData<>();
        initWrongQuestions();
    }

    private void initWrongQuestions() {
        Observable<ServiceResponse<GettedQuestions>> observable = new RetrofitClient.Builder().build().create(QuestionService.class).getNarrateQuestions("FUN", "EASY");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceResponse<GettedQuestions>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ServiceResponse<GettedQuestions> gettedQuestionsResponse) {
                        Result<GettedQuestions> gettedQuestions = new Result.Success<>(gettedQuestionsResponse.getData());
                        if (gettedQuestions instanceof Result.Success) {
                            GettedQuestions data = ((Result.Success<GettedQuestions>) gettedQuestions).getData();
                            questionsResult.setValue(new QuestionsResult(new GettedInQuestionsView(data.getQuestions())));
                        } else {
                            questionsResult.setValue(new QuestionsResult(R.string.failed));
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

    public LiveData<QuestionsResult> getQuestionsResult() {
        return questionsResult;
    }
}
