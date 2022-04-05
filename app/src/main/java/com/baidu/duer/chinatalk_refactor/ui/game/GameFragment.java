package com.baidu.duer.chinatalk_refactor.ui.game;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.core.content.ContextCompat;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;

import com.baidu.duer.chinatalk_refactor.R;

public class GameFragment extends FrameLayout {
    private View root;

    public GameFragment(Context context) {
        super(context);

        root = LayoutInflater.from(context).inflate(R.layout.fragment_game,null);
        int width = QMUIDisplayHelper.dp2px(context, 400);
        int height = QMUIDisplayHelper.dp2px(context, 800);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
        lp.gravity = Gravity.CENTER;
        addView(root, lp);

    }

    public void setImageResource(int imageResource) {
        QMUIRadiusImageView2 mRadiusImageView = root.findViewById(R.id.radiusImageView);
        mRadiusImageView.setImageResource(imageResource);
        mRadiusImageView.setBorderColor(
                ContextCompat.getColor(getContext(), R.color.radiusImageView_border_color));
        mRadiusImageView.setBorderWidth(QMUIDisplayHelper.dp2px(getContext(), 2));
        mRadiusImageView.setCornerRadius(QMUIDisplayHelper.dp2px(getContext(), 10));
        mRadiusImageView.setSelectedMaskColor(
                ContextCompat.getColor(getContext(), R.color.radiusImageView_selected_mask_color));
        mRadiusImageView.setSelectedBorderColor(
                ContextCompat.getColor(getContext(), R.color.radiusImageView_selected_border_color));
        mRadiusImageView.setSelectedBorderWidth(QMUIDisplayHelper.dp2px(getContext(), 3));
        mRadiusImageView.setTouchSelectModeEnabled(true);
        mRadiusImageView.setCircle(false);
    }
}
