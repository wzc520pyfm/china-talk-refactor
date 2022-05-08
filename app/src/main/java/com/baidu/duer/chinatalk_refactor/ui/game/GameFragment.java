package com.baidu.duer.chinatalk_refactor.ui.game;

import android.content.Context;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;

import com.baidu.duer.chinatalk_refactor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GameFragment extends FrameLayout {
    private View root;
    private Unbinder unbinder;

    @BindView(R.id.voice_input)
    LinearLayout voiceInput;

    public GameFragment(Context context) {
        super(context);

        root = LayoutInflater.from(context).inflate(R.layout.fragment_game,null);
        int width = QMUIDisplayHelper.dp2px(context, 400);
        int height = QMUIDisplayHelper.dp2px(context, 800);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
        lp.gravity = Gravity.CENTER;
        unbinder = ButterKnife.bind(this,root);
        addView(root, lp);

    }

    @OnClick(R.id.voice_btn)
    public void onClick1() {
        voiceInput.setVisibility(LinearLayout.VISIBLE);
    }
    @OnClick(R.id.close_input)
    public void onClick2() {
        voiceInput.setVisibility(LinearLayout.INVISIBLE);
    }

    public void setImageResource(int imageResource) {
        QMUIRadiusImageView2 mRadiusImageView = root.findViewById(R.id.radiusImageView); // 获取对象
        mRadiusImageView.setImageResource(imageResource); // 设置图片源
//        mRadiusImageView.setBorderColor(
//                ContextCompat.getColor(getContext(), R.color.radiusImageView_border_color)); // 描边粗细
//        mRadiusImageView.setBorderWidth(QMUIDisplayHelper.dp2px(getContext(), 2)); // 描边粗细
        mRadiusImageView.setCornerRadius(QMUIDisplayHelper.dp2px(getContext(), 10)); // 圆角弧度
//        mRadiusImageView.setSelectedMaskColor(
//                ContextCompat.getColor(getContext(), R.color.radiusImageView_selected_mask_color)); // 点击时的蒙版颜色
//        mRadiusImageView.setSelectedBorderColor(
//                ContextCompat.getColor(getContext(), R.color.radiusImageView_selected_border_color)); // 选中后的描边颜色
//        mRadiusImageView.setSelectedBorderWidth(QMUIDisplayHelper.dp2px(getContext(), 3)); // 选中图片后的描边粗细
        mRadiusImageView.setTouchSelectModeEnabled(false); // 是否允许点击选中
        mRadiusImageView.setCircle(false);
    }
}
