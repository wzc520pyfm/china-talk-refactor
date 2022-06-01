package com.baidu.duer.chinatalk_refactor.ui.setup;

import androidx.appcompat.app.AppCompatActivity;
import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.utils.SharedUtil;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@Route("setup")
public class SetupActivity extends AppCompatActivity {

    private SharedUtil sharedUtil = SharedUtil.getInstance();

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        ButterKnife.bind(this);
        mContext = this;
    }

    @OnClick(R.id.login_out_btn)
    public void onClick(View v) {
        sharedUtil.writeShared("token", "");
        Router.build("login").go(mContext);
    }
}