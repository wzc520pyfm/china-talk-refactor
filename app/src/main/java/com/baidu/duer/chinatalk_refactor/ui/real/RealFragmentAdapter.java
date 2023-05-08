package com.baidu.duer.chinatalk_refactor.ui.real;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.baidu.duer.chinatalk_refactor.bean.exam.Score;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;

import java.util.ArrayList;

public class RealFragmentAdapter extends QMUIPagerAdapter {
    private ArrayList<Score> scorePaper;
    private Context context;
    // 记录fragment的数组
    private ArrayList<RealFragment> fragments = new ArrayList<>();
    private final String[] words = {"A、","B、","C、","D、","E、","F、","G、"};

    public RealFragmentAdapter(Context context, ArrayList<Score> scorePaper) {
        super();
        this.context = context;
        this.scorePaper = scorePaper;
    }

    @NonNull
    @Override
    protected Object hydrate(@NonNull ViewGroup container, int position) {
         return new RealFragment(context,scorePaper.get(position).getQuestion().getId(), position);
    }

    @Override
    protected void populate(@NonNull ViewGroup container, @NonNull Object item, int position) {
        RealFragment questionView = (RealFragment) item;
        container.addView(questionView);
        fragments.add(questionView);
    }

    @Override
    protected void destroy(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return scorePaper.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * 依据index获取指定的fragment
     * @param position viewpager的index
     * @return 指定视图
     */
    public View getItemAt(int position) {
        return fragments.get(position);
    }

    public RealFragment getFragmentItemAt(int position) {
        return fragments.get(position);
    }
}
