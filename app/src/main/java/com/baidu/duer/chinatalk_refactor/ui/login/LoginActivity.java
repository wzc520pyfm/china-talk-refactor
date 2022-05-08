package com.baidu.duer.chinatalk_refactor.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baidu.duer.chinatalk_refactor.R;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route("login")
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    @BindView(R.id.login)
    QMUIRoundButton loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().hide(); // 在此页面隐藏安卓原生状态栏(当activity继承自AppCompatActivity时使用getSupportActionBar, 继承自Activity时使用getActionBar, 或者在AndroidManifest中单独为此activity设置一个无actionBar的主题)
        ButterKnife.bind(this);
        // 使用viewModel工厂
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        // final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginButton.setChangeAlphaWhenDisable(true); // 恢复点击态

        // 监听LoginFormState(表单验证状态类)数据,实时更新UI数据并实现一些额外表单行为
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid()); // 设置按钮是否激活
                if (loginFormState.getUsernameError() != null) { // 设置username输入框错误提示信息
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) { // 设置password输入框错误提示信息
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });
        // 监听LoginResult(登录结果)数据,实时更新UI数据并实现一些额外行为
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE); // 设置加载bar是否显示
                if (loginResult.getError() != null) { // 展示出错信息
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) { // 登录成功,将数据公开给UI,UI拿到数据执行一些操作
                    updateUiWithUser(loginResult.getSuccess());
                    finish();
                }
                setResult(Activity.RESULT_OK); // 向上一个activity传值,与startActivity恰好相反

                //Complete and destroy login activity once successful 销毁本页面
                // finish();
            }
        });

        // 监听文本变化
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { // 文本变化前
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { // 文本变化时
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) { // 文件变化后调用viewModel中的文本变化方法
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        // 为username和password输入框添加文本监听
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        // 当输入密码时,控制软键盘右下角显示的按键为IME_ACTION_DONE并设置点击事件
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) { // 软键盘IME_ACTION_DONE点击事件, 执行viewModel中的登录事件
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false; // 点击其他按键此方法均返回false
            }
        });

        // 登录按钮点击事件
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE); // 显示加载bar
                // 执行viewModel中的登录事件
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    /**
     * 登录成功,拿到用户信息,执行后续操作
     */
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Router.build("home").go(this); // 进入首页
    }

    private void showLoginFailed(@StringRes Integer errorString) { // 显示登录失败提示
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转到测试页
     */
    @OnClick(R.id.test)
    public void onClick() {
        Router.build("real").go(this);
    }

}