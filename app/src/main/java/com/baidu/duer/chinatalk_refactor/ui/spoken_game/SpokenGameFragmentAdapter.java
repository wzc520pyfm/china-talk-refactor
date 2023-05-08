package com.baidu.duer.chinatalk_refactor.ui.spoken_game;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.baidu.duer.chinatalk_refactor.bean.game.SpokenGame;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;

import java.util.ArrayList;

public class SpokenGameFragmentAdapter extends QMUIPagerAdapter {

    private ArrayList<SpokenGame> gamesList;
    private Context context;
    // 记录fragment的数组
    private ArrayList<SpokenGameFragment> fragments = new ArrayList<>();

    public SpokenGameFragmentAdapter(Context context, ArrayList<SpokenGame> gamesList) {
        super();
        this.context = context;
        this.gamesList = gamesList;

    }

    @NonNull
    @Override
    protected Object hydrate(@NonNull ViewGroup container, int position) {
        return new SpokenGameFragment(context);
    }

    @Override
    protected void populate(@NonNull ViewGroup container, @NonNull Object item, int position) {
        SpokenGameFragment gameView = (SpokenGameFragment) item;
        gameView.setImageResource(gamesList.get(position).getImgResource());
        container.addView(gameView);
        fragments.add(gameView);
    }

    @Override
    protected void destroy(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return gamesList.size();
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
