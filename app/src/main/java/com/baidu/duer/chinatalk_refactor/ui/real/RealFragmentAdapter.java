package com.baidu.duer.chinatalk_refactor.ui.real;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.bean.real.RealQuestion;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;

import java.util.ArrayList;

public class RealFragmentAdapter extends QMUIPagerAdapter {
    private ArrayList<RealQuestion> questionList;
    private Context context;
    private final String[] words = {"A、","B、","C、","D、","E、","F、","G、"};

    public RealFragmentAdapter(Context context, ArrayList<RealQuestion> questionList) {
        super();
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    protected Object hydrate(@NonNull ViewGroup container, int position) {
        return new RealFragment(context,questionList.get(position));
    }

    @Override
    protected void populate(@NonNull ViewGroup container, @NonNull Object item, int position) {
        RealFragment questionView = (RealFragment) item;
        container.addView(questionView);
    }

    @Override
    protected void destroy(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return questionList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
