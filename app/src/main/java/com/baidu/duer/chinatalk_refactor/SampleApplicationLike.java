
package com.baidu.duer.chinatalk_refactor;


import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.entry.DefaultApplicationLike;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.io.IOException;
import java.lang.ref.WeakReference;

import cn.bmob.v3.Bmob;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

@DefaultLifeCycle(application = "com.baidu.duer.chinatalk_refactor.SampleApplication", flags = ShareConstants.TINKER_ENABLE_ALL, loadVerifyFlag = false)
public class SampleApplicationLike extends DefaultApplicationLike {
    private static final String TAG = "Tinker.SampleApplicationLike";
    private static WeakReference<Context> mContext;

    public SampleApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                                 long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = new WeakReference<Context>(this.getApplication());
        // 这里实现SDK初始化
        //Bmob后端云服务初始化
        Bmob.initialize(mContext.get(), mContext.get().getString(R.string.bmobKey));
        // 初始化讯飞SDK: 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(mContext.get(), SpeechConstant.APPID + "=" + mContext.get().getString(R.string.APPID));
        // 初始化arch
        QMUISwipeBackActivityManager.init(this.getApplication());
        setRxJavaErrorHandler();
    }

    /**
     * 获取全局上下文*/
    public static Context getContext() {
        return mContext.get();
    }

    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     *
     * @param base
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);

        TinkerManager.setTinkerApplicationLike(this);
        TinkerManager.setUpgradeRetryEnable(true);
        TinkerManager.installTinker(this);
        Tinker.with(getApplication());
    }

    /**
     * 对RXJava的错误处理
     */
    private void setRxJavaErrorHandler() {
        if (RxJavaPlugins.getErrorHandler() != null || RxJavaPlugins.isLockdown()) {
            Log.d("App", "setRxJavaErrorHandler getErrorHandler()!=null||isLockdown()");
            return;
        }
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable e) {
                if (e instanceof UndeliverableException) {
                    e = e.getCause();
                    Log.d("App", "setRxJavaErrorHandler UndeliverableException=" + e);
                    return;
                } else if ((e instanceof IOException)) {
                    // fine, irrelevant network problem or API that throws on cancellation
                    return;
                } else if (e instanceof InterruptedException) {
                    // fine, some blocking code was interrupted by a dispose call
                    return;
                } else if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                    // that's likely a bug in the application
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler =
                            Thread.currentThread().getUncaughtExceptionHandler();
                    if (uncaughtExceptionHandler != null) {
                        uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e);
                    }
                    return;
                } else if (e instanceof IllegalStateException) {
                    // that's a bug in RxJava or in a custom operator
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler =
                            Thread.currentThread().getUncaughtExceptionHandler();
                    if (uncaughtExceptionHandler != null) {
                        uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e);
                    }
                    return;
                }
                Log.d("App", "setRxJavaErrorHandler unknown exception=" + e);
            }
        });
    }
}

