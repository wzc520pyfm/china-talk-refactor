package com.baidu.duer.chinatalk_refactor.ui.register;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.airbnb.lottie.LottieAnimationView;
import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.base.BaseActivity;
import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

@Route("register")
public class RegisterActivity extends BaseActivity implements View.OnTouchListener {

    private String TAG = "LoginActivity";
    private RegisterViewModel registerViewModel;
    @BindView(R.id.container)
    ConstraintLayout layout;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.ver_code)
    EditText verCode;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.send_ver)
    QMUIRoundButton sendVer;
    @BindView(R.id.register)
    QMUIRoundButton register;
    @BindView(R.id.loading)
    LottieAnimationView loadingView;
    private GestureDetector mGestureDetector;
    private Context mContext;

    @BindString(R.string.sendVer)
    String sendVerTip;
    private static final int FLING_MIN_DISTANCE = 50;
    private static final int FLING_MIN_VELOCITY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = this;

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        mGestureDetector = new GestureDetector(this, myGestureListener);
        layout.setOnTouchListener(this);
        layout.setLongClickable(true);//必需设置这为true 否则也监听不到手势
        sendVer.setChangeAlphaWhenDisable(true);
        // register.setChangeAlphaWhenDisable(true);
        // register.setEnabled(false);
        registerViewModel.getRegisterResult().observe(this, new Observer<ServiceResponse>() {
            @Override
            public void onChanged(ServiceResponse serviceResponse) {
                loadingView.setVisibility(View.GONE);
                showTip(serviceResponse.getMessage());
                if(serviceResponse.getCode() == 200) {
                    backLogin();
                }
            }
        });
        registerViewModel.getCountDown().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                sendVer.setVisibility(View.VISIBLE);
                if(!s.equals("-1")) {
                    sendVer.setText(s);
                    sendVer.setEnabled(false);
                } else {
                    sendVer.setText(sendVerTip);
                    sendVer.setEnabled(true);
                }
            }
        });
    }

    @OnClick({R.id.send_ver, R.id.register})
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.send_ver:
                sendVerCode();
                break;
            case R.id.register:
                if(password.getText().toString().length() > 5 && username.getText().toString().length() == 11) {
                    verifyCode();
                } else {
                    password.setError("密码长度需大于5");
                }
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * 发送验证码
     */
    private void sendVerCode() {
        if(username.getText().toString().length() < 11) {
            username.setError("手机号格式错误");
            return;
        }
        loadingView.setVisibility(View.VISIBLE);
        BmobSMS.requestSMSCode(username.getText().toString(), "登录验证码", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                loadingView.setVisibility(View.GONE);
                if (e == null) {
                    sendVer.animate()
                            .alpha(0f)
                            .setDuration(500)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    sendVer.setVisibility(View.GONE);
                                }
                            });
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            registerViewModel.startDownTime();
                        }
                    }, 500);
                    String success = String.format("发送验证码成功，短信ID：%s \n", smsId);
                    Log.d("RegActivity",success);
                    showTip(success);
                } else {
                    String error = String.format("发送验证码失败： %s - %s \n", e.getErrorCode(), e.getMessage());
                    Log.d("RegActivity",error);
                    showTip(error);
                }
            }
        });
    }

    /**
     * 验证验证码
     */
    private void verifyCode() {
        loadingView.setVisibility(View.VISIBLE);
        BmobSMS.verifySmsCode(username.getText().toString(), verCode.getText().toString(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                loadingView.setVisibility(View.GONE);
                if (e == null) {
                    showTip("验证码通过");
                    register();
                } else {
                    showTip("验证码验证失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                }
            }
        });
    }

    /**
     * 注册
     */
    private void register() {
        loadingView.setVisibility(View.VISIBLE);
        registerViewModel.register(username.getText().toString(), password.getText().toString());
    }

    /**
     * 滑动监听
     */
    GestureDetector.SimpleOnGestureListener myGestureListener = new GestureDetector.SimpleOnGestureListener(){
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            Log.e(TAG, "开始滑动");
            float x = e1.getX()-e2.getX();
            float x2 = e2.getX()-e1.getX();
            if(x > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
                Log.i(TAG,"向左手势");
                backLogin();
            }else if(x2 > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
                Log.i(TAG,"向右手势");
            }

            return false;
        };
    };

    /**
     * 返回登录页
     */
    private void backLogin() {
        Router.build("login").anim(R.anim.right_in, R.anim.left_out).go(mContext);
    }

    /**
     * 显示提示
     */
    private void showTip(String tipString) {
        Toast.makeText(getApplicationContext(), tipString, Toast.LENGTH_SHORT).show();
        Log.d("RegActivity",tipString);
    }
}