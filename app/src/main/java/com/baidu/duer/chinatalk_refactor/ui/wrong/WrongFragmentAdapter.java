package com.baidu.duer.chinatalk_refactor.ui.wrong;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.baidu.duer.chinatalk_refactor.bean.game.SpokenGame;
import com.baidu.duer.chinatalk_refactor.bean.record.WrongQuestionRecords;
import com.baidu.duer.chinatalk_refactor.ui.spoken_game.SpokenGameFragment;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;

import java.util.ArrayList;

public class WrongFragmentAdapter extends QMUIPagerAdapter {

    private ArrayList<WrongQuestionRecords> wrongQuestionList;
    private Context context;
    // 记录fragment的数组
    private ArrayList<WrongFragment> fragments = new ArrayList<>();

    public WrongFragmentAdapter(Context context, ArrayList<WrongQuestionRecords> wrongQuestionList) {
        super();
        this.context = context;
        this.wrongQuestionList = wrongQuestionList;

    }

    @NonNull
    @Override
    protected Object hydrate(@NonNull ViewGroup container, int position) {
        return new WrongFragment(context, position);
    }

    @Override
    protected void populate(@NonNull ViewGroup container, @NonNull Object item, int position) {
        WrongFragment wrongView = (WrongFragment) item;
        wrongView.setWrongAnswer(wrongQuestionList.get(position).getLastWrongAnswer());
        wrongView.setQuestion(wrongQuestionList.get(position).getQuestion());
        wrongView.setExam(wrongQuestionList.get(position).getExamPaper());
        container.addView(wrongView);
        fragments.add(wrongView);
    }

    @Override
    protected void destroy(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return wrongQuestionList.size();
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
}
