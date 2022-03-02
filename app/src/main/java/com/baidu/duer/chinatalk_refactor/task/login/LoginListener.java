package com.baidu.duer.chinatalk_refactor.task.login;

import com.baidu.duer.chinatalk_refactor.bean.Result;
import com.baidu.duer.chinatalk_refactor.bean.user.LoggedInUser;

public interface LoginListener {

    default void onTaskComplete(Result<LoggedInUser> loggedInUserResult, String result){}

    default void onTaskCancel(String result){}

    default void onTaskUpdate(String request, Integer progress, Integer subProgress){}

    default void onTaskBegin(String request){}
}
