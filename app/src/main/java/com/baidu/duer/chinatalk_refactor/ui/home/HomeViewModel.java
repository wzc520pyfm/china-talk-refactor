package com.baidu.duer.chinatalk_refactor.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//创建ViewModel类千万不能持有Context的引用，否则会引起内存泄漏，如果需要使用Context可以继承AndroidViewModel。
public class HomeViewModel extends ViewModel {
    // 数据获取和处理包含在ViewModel中
    public HomeViewModel() {

    }
}