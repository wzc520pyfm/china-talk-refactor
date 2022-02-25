package com.baidu.duer.chinatalk_refactor.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.iflytek.RecognizeListener;
import com.baidu.duer.chinatalk_refactor.iflytek.RecognizeSpeechManager;
import com.iflytek.cloud.SpeechError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements RecognizeListener {
    //UI视图的展示和事件包含在Fragment或Activity中

    private Unbinder unbinder;
    private HomeViewModel homeViewModel;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.text_home)
    TextView textView;

    public Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getContext();
        //构建ViewModel实例
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        //创建视图对象
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // fragment绑定butterKnife
        unbinder = ButterKnife.bind(this,root);
        //让TextView观察ViewModel中数据的变化,并实时展示
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        homeViewModel.getRecognizeText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvContent.setText(s);
            }
        });

        //初始化讯飞音频读写管理类
        RecognizeSpeechManager.instance().init(mContext);
        RecognizeSpeechManager.instance().setRecognizeListener(this);

        //返回视图对象
        return root;
    }

    @OnClick({R.id.btStart, R.id.btCancel, R.id.btStop})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btStart:
                RecognizeSpeechManager.instance().startRecognize();
                break;
            case R.id.btCancel:
                RecognizeSpeechManager.instance().cancelRecognize();
                break;
            case R.id.btStop:
                RecognizeSpeechManager.instance().stopRecognize();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();//视图销毁时必须解绑
        RecognizeSpeechManager.instance().release();
    }

    @Override
    public void onNewResult(String result) {
        homeViewModel.setRecognizeText(homeViewModel.getRecognizeText().getValue() + "最新翻译：" + result + "\n");
    }

    @Override
    public void onTotalResult(String result, boolean isLast) {
        homeViewModel.setRecognizeText(homeViewModel.getRecognizeText().getValue() + "所有翻译：" + result + "\n");
    }

    @Override
    public void onError(SpeechError speechError) {
        Toast.makeText(mContext, "出错了 " + speechError, Toast.LENGTH_SHORT).show();
    }
}