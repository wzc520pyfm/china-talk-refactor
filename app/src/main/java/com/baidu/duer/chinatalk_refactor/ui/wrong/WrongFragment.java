package com.baidu.duer.chinatalk_refactor.ui.wrong;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.bean.question.Question;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public class WrongFragment extends FrameLayout {
    private View root;
    Context mContext;

    public WrongFragment(Context context, Question question) {
        super(context);
        this.mContext = context;

        root = LayoutInflater.from(context).inflate(R.layout.fragment_wrong,null, false);
        int width = QMUIDisplayHelper.dp2px(context, 400);
        int height = QMUIDisplayHelper.dp2px(context, 800);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
        lp.gravity = Gravity.CENTER;

        addView(root, lp);
    }
}
