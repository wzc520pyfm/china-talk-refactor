package com.baidu.duer.chinatalk_refactor.ui.exam;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.api.ExamPaperService;
import com.baidu.duer.chinatalk_refactor.bean.Result;
import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.exam.GettedExams;
import com.baidu.duer.chinatalk_refactor.http.RetrofitClient;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExamViewModel extends ViewModel {

    private MutableLiveData<ExamsResult> examResult;

    public ExamViewModel() {
        examResult = new MutableLiveData<>();
        initExams();
    }

    public LiveData<ExamsResult> getExamResult() {
        return examResult;
    }

    private void initExams() {
        Observable<ServiceResponse<GettedExams>> observable = new RetrofitClient.Builder().build().create(ExamPaperService.class).getAllHskMocksWithUserScoreAll();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceResponse<GettedExams>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ServiceResponse<GettedExams> examServiceResponse) {
                        Result<GettedExams> gettedExam = new Result.Success<>(examServiceResponse.getData());
                        if (gettedExam instanceof Result.Success) {
                            GettedExams data = ((Result.Success<GettedExams>) gettedExam).getData();
                            examResult.setValue(new ExamsResult(new GettedInExamsView(data.getPapers())));
                        } else {
                            examResult.setValue(new ExamsResult(R.string.failed));
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

//    public ArrayList<Exam> onRefreshData() {
//        ArrayList<Exam> data = new ArrayList<>();
//        for(int i = 0; i < 5; i++){
//            Exam exam = new Exam(0, "refresh试卷-" + i, 10, 10, 60);
//            data.add(exam);
//        }
//        return data;
//    }
//    public ArrayList<Exam> onLoadMore() {
//        ArrayList<Exam> data = new ArrayList<>();
//        for(int i = 0; i < 5; i++){
//            Exam exam = new Exam(0, "load试卷-" + i, 10, 10, 60);
//            data.add(exam);
//        }
//        return data;
//    }

}