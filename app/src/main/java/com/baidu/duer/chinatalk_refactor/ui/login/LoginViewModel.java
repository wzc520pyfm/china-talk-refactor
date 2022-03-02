package com.baidu.duer.chinatalk_refactor.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.bean.Result;
import com.baidu.duer.chinatalk_refactor.bean.user.LoginData;
import com.baidu.duer.chinatalk_refactor.bean.user.LoggedInUser;
import com.baidu.duer.chinatalk_refactor.task.login.LoginListener;
import com.baidu.duer.chinatalk_refactor.task.login.LoginTask;


public class LoginViewModel extends ViewModel implements LoginListener {

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
        LoginTask asyncTask = new LoginTask();
        asyncTask.setLoginListener(this);
        asyncTask.execute(new LoginData(username, password));
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
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check. 检查password
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    @Override
    public void onTaskComplete(Result<LoggedInUser> loggedInUserResult, String result) {
        if (loggedInUserResult instanceof Result.Success) { // 如果登录结果信息是success
            LoggedInUser data = ((Result.Success<LoggedInUser>) loggedInUserResult).getData(); // 取出user信息
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getToken()))); // 从取出的信息中再取出向UI公开的数据项
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed)); // 登录失败
        }
    }
}