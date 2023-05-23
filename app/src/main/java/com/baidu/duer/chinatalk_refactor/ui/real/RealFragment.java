package com.baidu.duer.chinatalk_refactor.ui.real;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.api.QuestionService;
import com.baidu.duer.chinatalk_refactor.bean.Result;
import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.question.GettedInQuestionView;
import com.baidu.duer.chinatalk_refactor.bean.question.GettedQuestion;
import com.baidu.duer.chinatalk_refactor.bean.question.Question;
import com.baidu.duer.chinatalk_refactor.bean.question.QuestionResult;
import com.baidu.duer.chinatalk_refactor.http.RetrofitClient;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RealFragment extends FrameLayout {
    private View root;
    Context mContext;

    private Integer position;
    private String answer;
    private Integer questionId;

    public RealFragment(Context context, Integer questionId, Integer position) {
        super(context);
        this.mContext = context;
        this.position = position;

        root = LayoutInflater.from(context).inflate(R.layout.fragment_real,null, false);
        int width = QMUIDisplayHelper.dp2px(context, 400);
        int height = QMUIDisplayHelper.dp2px(context, 800);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
        lp.gravity = Gravity.CENTER;

        addView(root, lp);
        getQuestion(questionId);
    }

    private void getQuestion(Integer questionId) {
        Observable<ServiceResponse<GettedQuestion>> observable = new RetrofitClient.Builder().build().create(QuestionService.class).getOneQuestion(questionId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceResponse<GettedQuestion>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ServiceResponse<GettedQuestion> gettedQuestionServiceResponse) {
                        QuestionResult questionResult;
                        Result<GettedQuestion> gettedQuestion = new Result.Success<>(gettedQuestionServiceResponse.getData());
                        if (gettedQuestion instanceof Result.Success) {
                            GettedQuestion data = ((Result.Success<GettedQuestion>) gettedQuestion).getData();
                            questionResult = new QuestionResult(new GettedInQuestionView(data.getQuestion()));
                        } else {
                            questionResult = new QuestionResult(R.string.failed);
                        }
                        if(questionResult.getSuccess() != null) {
                            setRealQuestion(questionResult.getSuccess().getQuestion());
                        }
                        if( questionResult.getError() != null) {
                            showLoginFailed(questionResult.getError());
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

    private void setRealQuestion(Question question) {

        RadioGroup itemGroup = findViewById(R.id.item_group);
        TextView questionTv = findViewById(R.id.question);
        questionTv.setText((position + 1) + ". " + question.getSelectQuestion().getContent());
        ArrayList<String> items = question.getSelectQuestion().getItems();
        for(int i = 0; i < items.size(); i++) {
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,0,0,0);
            answer = question.getSelectQuestion().getAnswer();
            questionId = question.getId();
            RadioButton radioButton = new RadioButton(getContext());
            // radioButton.setButtonDrawable(R.drawable.ic_circle);
            radioButton.setId(i); //必须要设置一个ID
            radioButton.setPadding(50, 0, 0, 0);
            radioButton.setMinWidth(1000);
            radioButton.setMinHeight(150);
            radioButton.setText(words[i] + question.getSelectQuestion().getItems().get(i));
            radioButton.setTextColor(getResources().getColor(R.color.blackWord));
            itemGroup.addView(radioButton, lp);
        }
    }

    /**
     * 获取当前页题目的id
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * 获取当前页题目的答案
     */
    public String getAnswer() {
        return answer;
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(mContext, errorString, Toast.LENGTH_SHORT).show();
    }

    private final String[] words = {"A、","B、","C、","D、","E、","F、","G、"};
}
