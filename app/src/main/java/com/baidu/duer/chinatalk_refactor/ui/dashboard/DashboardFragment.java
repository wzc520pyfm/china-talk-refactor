package com.baidu.duer.chinatalk_refactor.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.iflytek.SynthesizeListener;
import com.baidu.duer.chinatalk_refactor.iflytek.SynthesizeSpeechManager;
import com.iflytek.cloud.SpeechError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DashboardFragment extends Fragment implements SynthesizeListener {

    private Unbinder unbinder;
    private DashboardViewModel dashboardViewModel;
    @BindView(R.id.etEva)
    EditText editText;

    public Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getContext();
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        unbinder = ButterKnife.bind(this,root);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                editText.setText(s);
            }
        });

        //初始化讯飞音频合成管理类
        SynthesizeSpeechManager.instance().init(mContext);
        SynthesizeSpeechManager.instance().setSynthesizeListener(this);

        return root;
    }

    @OnClick({R.id.btParse, R.id.btPaused, R.id.btResumed})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btParse:
                SynthesizeSpeechManager.instance().startSpeak(dashboardViewModel.getText().getValue());
                break;
            case R.id.btPaused:
                SynthesizeSpeechManager.instance().pauseSpeak();
                break;
            case R.id.btResumed:
                SynthesizeSpeechManager.instance().resumeSpeak();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder != null) {
            unbinder.unbind();//视图销毁时必须解绑
        }
        SynthesizeSpeechManager.instance().release();
    }

    @Override
    public void onError(SpeechError speechError) {
        Toast.makeText(mContext, "出错了 " + speechError, Toast.LENGTH_SHORT).show();
    }

}