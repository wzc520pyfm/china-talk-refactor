package com.baidu.duer.chinatalk_refactor.task.login;

import android.os.AsyncTask;

import com.baidu.duer.chinatalk_refactor.bean.Result;
import com.baidu.duer.chinatalk_refactor.bean.user.LoginData;
import com.baidu.duer.chinatalk_refactor.data.login.LoginRepository;
import com.baidu.duer.chinatalk_refactor.bean.user.LoggedInUser;


/**
 * 登录线程
 */
public class LoginTask extends AsyncTask<LoginData, Integer, Result<LoggedInUser>> {
    // 唯一标识
    private final static String TAG = "LoginTask";
    // 任务监听器
    private LoginListener loginListener;

    public LoginTask() {
        super();
    }

    // 设置监听器
    public void setLoginListener(LoginListener listener) {
        loginListener = listener;
    }

    // 后台执行线程
    @Override
    protected Result<LoggedInUser> doInBackground(LoginData... params) {
        LoginData loginData = params[0];
        return LoginRepository.getInstance().login(loginData);
    }

    // 启动线程
    protected void onPreExecute() {
        loginListener.onTaskBegin(TAG);
    }

    // 线程通报处理进展
    protected void onProgressUpdate(Integer... values) {
        loginListener.onTaskUpdate(TAG, values[0], 0);
    }

    // 线程处理完成
    protected void onPostExecute(Result<LoggedInUser> loggedInUserResult) {
        loginListener.onTaskComplete(loggedInUserResult, TAG);
    }

    // 线程取消
    protected void onCancelled(String result) {
        loginListener.onTaskCancel(result);
    }

}
