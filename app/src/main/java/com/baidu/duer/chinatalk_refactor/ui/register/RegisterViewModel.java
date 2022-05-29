package com.baidu.duer.chinatalk_refactor.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.baidu.duer.chinatalk_refactor.api.LoginService;
import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.http.RetrofitClient;
import com.baidu.duer.chinatalk_refactor.utils.SharedUtil;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends ViewModel {

    private SharedUtil sharedUtil = SharedUtil.getInstance();
    // 注册结果
    private MutableLiveData<ServiceResponse> registerResult = new MutableLiveData<>();

    /**
     * 注册
     */
    public void register(String username, String password) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", username);
        map.put("password", password);
        Observable<ServiceResponse> observable = new RetrofitClient.Builder().build().create(LoginService.class).register(map);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ServiceResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ServiceResponse serviceResponse) {
                        registerResult.setValue(serviceResponse);
                        sharedUtil.writeShared("user_phone", username);
                        sharedUtil.writeShared("password", password);
                    }

                    @Override
                    public void onError(Throwable e) {
                        sharedUtil.writeShared("user_phone", "");
                        sharedUtil.writeShared("password", "");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<ServiceResponse> getRegisterResult() {
        return registerResult;
    }
}
