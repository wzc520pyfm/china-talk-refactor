package com.baidu.duer.chinatalk_refactor.ui.my;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<String> evaluateText;

    public MyViewModel() {
        evaluateText = new MutableLiveData<>();
    }


    public LiveData<String> getEvaluateText() {
        return evaluateText;
    }


    public void setEvaluateText(String evaluateText) {
        this.evaluateText.setValue(evaluateText);
    }
}