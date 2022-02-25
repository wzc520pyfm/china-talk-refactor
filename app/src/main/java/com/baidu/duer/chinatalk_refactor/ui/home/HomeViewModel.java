package com.baidu.duer.chinatalk_refactor.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//创建ViewModel类千万不能持有Context的引用，否则会引起内存泄漏，如果需要使用Context可以继承AndroidViewModel。
public class HomeViewModel extends ViewModel {
    // 数据获取和处理包含在ViewModel中

    private MutableLiveData<String> mText;
    // 识别结果数据
    private MutableLiveData<String> recognizeText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        recognizeText = new MutableLiveData<>();
        //模拟从网络加载用户信息--网络请求应另外封装一个方法
        mText.setValue("This is home fragment");
        recognizeText.setValue("");
    }
    //也可以包含一些对数据的处理方法

    // get方法
    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getRecognizeText () {
        return recognizeText;
    }

    // set方法
    public void setRecognizeText (String recognizeText) {
        this.recognizeText.setValue(recognizeText);
    }
}