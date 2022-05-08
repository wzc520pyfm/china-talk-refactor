package com.baidu.duer.chinatalk_refactor.ui.my;

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
import com.iflytek.cloud.SpeechError;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyFragment extends Fragment {

    private Unbinder unbinder;
    private MyViewModel notificationsViewModel;
    private View root;

    @BindView(R.id.pic_bg)
    QMUILinearLayout picBg;
    @BindView(R.id.notice)
    QMUILinearLayout notice;

    public Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getContext();
        notificationsViewModel =
                ViewModelProviders.of(this).get(MyViewModel.class);
        root = inflater.inflate(R.layout.fragment_my, container, false);
        // fragment绑定butterKnife
        unbinder = ButterKnife.bind(this,root);

        initStyle();
        return root;
    }

    private void initStyle() {
        picBg.setShadowColor(0xff0000ff); // 阴影色
        picBg.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 50),
                QMUIDisplayHelper.dp2px(getActivity(), 10), 0.4f);
        notice.setShadowColor(0xff0000ff); // 阴影色
        notice.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 15),
                QMUIDisplayHelper.dp2px(getActivity(), 10), 0.4f);
        setImageResource(R.drawable.example);
    }


    public void setImageResource(int imageResource) {
        QMUIRadiusImageView2 mRadiusImageView = root.findViewById(R.id.user_pic); // 获取对象
        mRadiusImageView.setImageResource(imageResource); // 设置图片源
        mRadiusImageView.setCornerRadius(QMUIDisplayHelper.dp2px(getContext(), 50)); // 圆角弧度
        mRadiusImageView.setTouchSelectModeEnabled(false); // 是否允许点击选中
        mRadiusImageView.setCircle(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder != null) {
            unbinder.unbind();//视图销毁时必须解绑
        }
    }
}