package com.baidu.duer.chinatalk_refactor.ui.spoken_game;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.bean.game.SpokenGame;
import com.baidu.duer.chinatalk_refactor.bean.question.Question;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.ButterKnife;

public class SpokenGameFragmentAdapter extends QMUIPagerAdapter {

    private ArrayList<Question> questions;
    private Context context;
    @BindString(R.string.assetsUrl)
    public String assetsUrl;
    // 记录fragment的数组
    private ArrayList<SpokenGameFragment> fragments = new ArrayList<>();

    public SpokenGameFragmentAdapter(Context context, View convertView, ArrayList<Question> questions) {
        super();
        ButterKnife.bind(this, convertView);
        this.context = context;
        this.questions = questions;

    }

    @NonNull
    @Override
    protected Object hydrate(@NonNull ViewGroup container, int position) {
        return new SpokenGameFragment(context);
    }

    @Override
    protected void populate(@NonNull ViewGroup container, @NonNull Object item, int position) {
        SpokenGameFragment gameView = (SpokenGameFragment) item;
        gameView.setImageResource(assetsUrl + "/" +questions.get(position).getContentResources().get(0).getFileResource().getPath());
        gameView.setGameContent(questions.get(position).getNarrateQuestion().getContent());
        gameView.setAnswer(questions.get(position).getNarrateQuestion().getAnswer());
        gameView.setTip(questions.get(position).getNarrateQuestion().getTip());
        gameView.setAnalysis(questions.get(position).getNarrateQuestion().getAnalysis());
        container.addView(gameView);
        fragments.add(gameView);
    }

    @Override
    protected void destroy(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return questions.size();
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

    public SpokenGameFragment getItem(int position) { return fragments.get(position); }
}
