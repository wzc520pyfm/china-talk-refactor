package com.baidu.duer.chinatalk_refactor.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> evaluateText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        evaluateText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
        evaluateText.setValue("西瓜");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getEvaluateText() {
        return evaluateText;
    }

    public void setText(String mText) {
        this.mText.setValue(mText);
    }

    public void setEvaluateText(String evaluateText) {
        this.evaluateText.setValue(evaluateText);
    }
}