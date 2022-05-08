package com.baidu.duer.chinatalk_refactor.ui.real;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.bean.real.RealQuestion;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.ArrayList;

import butterknife.BindView;

public class RealFragment extends FrameLayout {
    private View root;
    Context mContext;

    public RealFragment(Context context, RealQuestion realQuestion) {
        super(context);
        this.mContext = context;

        root = LayoutInflater.from(context).inflate(R.layout.fragment_real,null, false);
        int width = QMUIDisplayHelper.dp2px(context, 400);
        int height = QMUIDisplayHelper.dp2px(context, 800);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
        lp.gravity = Gravity.CENTER;

        addView(root, lp);
        setRealQuestion(realQuestion);
    }

    private void setRealQuestion(RealQuestion realQuestion) {
        RadioGroup itemGroup = findViewById(R.id.item_group);
        TextView questionTv = findViewById(R.id.question);
        questionTv.setText(realQuestion.getContent());
        ArrayList<String> items = realQuestion.getItems();
        for(int i = 0; i < items.size(); i++) {
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,0,0,0);
            RadioButton radioButton = new RadioButton(getContext());
            // radioButton.setButtonDrawable(R.drawable.ic_circle);
            radioButton.setId(i); //必须要设置一个ID
            radioButton.setPadding(50, 0, 0, 0);
            radioButton.setMinWidth(1000);
            radioButton.setMinHeight(150);
            radioButton.setText(words[i] + realQuestion.getItems().get(i));
            radioButton.setTextColor(getResources().getColor(R.color.blackWord));
            itemGroup.addView(radioButton, lp);
        }
    }

    private final String[] words = {"A、","B、","C、","D、","E、","F、","G、"};
}
