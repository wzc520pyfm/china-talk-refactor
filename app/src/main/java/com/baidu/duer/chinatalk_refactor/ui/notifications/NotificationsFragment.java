package com.baidu.duer.chinatalk_refactor.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.iflytek.EvaluateListener;
import com.baidu.duer.chinatalk_refactor.iflytek.EvaluateSpeechManager;
import com.baidu.duer.chinatalk_refactor.iflytek.RecognizeSpeechManager;
import com.iflytek.cloud.SpeechError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NotificationsFragment extends Fragment implements EvaluateListener {

    private Unbinder unbinder;
    private NotificationsViewModel notificationsViewModel;

    @BindView(R.id.etEva)
    EditText etEva;

    public Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getContext();
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        // fragment绑定butterKnife
        unbinder = ButterKnife.bind(this,root);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        notificationsViewModel.getEvaluateText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                etEva.setText(s);
            }
        });

        //初始化讯飞音频评测管理类
        EvaluateSpeechManager.instance().init(mContext);
        EvaluateSpeechManager.instance().setEvaluateListener(this);

        return root;
    }

    @OnClick({R.id.btStart, R.id.btCancel, R.id.btStop, R.id.btParse})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btStart:
                EvaluateSpeechManager.instance().startEvaluate("read_word",notificationsViewModel.getEvaluateText().getValue());
                break;
            case R.id.btCancel:
                EvaluateSpeechManager.instance().cancelEvaluate();
                break;
            case R.id.btStop:
                EvaluateSpeechManager.instance().stopEvaluate();
                break;
            case R.id.btParse:
                notificationsViewModel.setText(EvaluateSpeechManager.instance().parseResult().toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();//视图销毁时必须解绑
        EvaluateSpeechManager.instance().release();
    }

    @Override
    public void onNewResult(String result) {
        notificationsViewModel.setText(result);
    }

    @Override
    public void onTotalResult(String result, boolean isLast) {
        // Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(SpeechError speechError) {
        Toast.makeText(mContext, "出错了 " + speechError, Toast.LENGTH_SHORT).show();
    }
}