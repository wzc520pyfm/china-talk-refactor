package com.baidu.duer.chinatalk_refactor.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.api.LoginService;
import com.baidu.duer.chinatalk_refactor.bean.Result;
import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.user.LoginData;
import com.baidu.duer.chinatalk_refactor.bean.user.LoggedInUser;
import com.baidu.duer.chinatalk_refactor.http.Http;
import com.baidu.duer.chinatalk_refactor.http.RetrofitClient;
import com.baidu.duer.chinatalk_refactor.utils.SharedUtil;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.HTTP;


public class LoginViewModel extends ViewModel {

    private SharedUtil sharedUtil = SharedUtil.getInstance();

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    LiveData<LoginFormState> getLoginFormState() { // 表单验证状态数据
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() { // 登录结果数据
        return loginResult;
    }

    // 登录事件, 获取登录结果信息
    public void login(String username, String password) {
        // can be launched in a separate asynchronous job. 可以在单独的异步作业中启动
        Observable<ServiceResponse<LoggedInUser>> observable = new RetrofitClient.Builder().build().create(LoginService.class).login(new LoginData(username, password));
        observable.subscribeOn(Schedulers.io()) // 发送事件的线程: io操作的线程
                .observeOn(AndroidSchedulers.mainThread()) // 接收事件的线程: Android的主线程
                .subscribe(new Observer<ServiceResponse<LoggedInUser>>() { // TODO 当返回的数据不是成功时的数据时,不会执行下面的回调函数,待解决
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(ServiceResponse<LoggedInUser> arrayListServiceResponse) {
                        Result<LoggedInUser> loggedInUser;
                        if(arrayListServiceResponse.getCode() == Http.SUCCESS) {
                            loggedInUser = new Result.Success<>(arrayListServiceResponse.getData());
                        } else {
                            loggedInUser = new Result.Error(new Exception(arrayListServiceResponse.getMessage()));
                        }
                        if (loggedInUser instanceof Result.Success) { // 如果登录结果信息是success
                            LoggedInUser data = ((Result.Success<LoggedInUser>) loggedInUser).getData(); // 取出user信息
                            sharedUtil.writeShared("token", "Bearer " + data.getToken()); // 存储token
                            sharedUtil.writeShared("user_phone", username);
                            sharedUtil.writeShared("password", password);
                            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getToken()))); // 从取出的信息中再取出向UI公开的数据项
                        } else {
                            loginResult.setValue(new LoginResult(R.string.login_failed));
                        }
                    }
                    @Override
                    public void onError(Throwable e) { }
                    @Override
                    public void onComplete() { }
                });
    }

    // 当login表单数据变化时调用此方法
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) { // 未通过username检查时设置LoginFormStated的username错误提示
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) { // 未通过username检查时设置LoginFormStated的password错误提示
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else { // 通过检查则设置LoginFormStated的isDataValid为true
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check. 检查username
    private boolean isUserNameValid(String username) {
        if (username.trim().isEmpty()) {
            return false;
        }
        if (username.length() == 11) {
            return Patterns.PHONE.matcher(username).matches();
        }
        return false;
    }

    // A placeholder password validation check. 检查password
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}