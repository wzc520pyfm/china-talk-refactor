package com.baidu.duer.chinatalk_refactor.ui.real;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * ViewModel工厂, 如果需要在activity或fragment中初始化viewModel时携带自定义参数则需要通过此工厂
 * 注意: 不推荐创建公用的ViewModel工厂, 而是单一ViewModel对应单一工厂, 更加安全
 */
public class RealViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private Integer paperId;

    public RealViewModelFactory(Application application, Integer paperId) {
        this.application = application;
        this.paperId = paperId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RealViewModel(application, paperId);
    }
}
