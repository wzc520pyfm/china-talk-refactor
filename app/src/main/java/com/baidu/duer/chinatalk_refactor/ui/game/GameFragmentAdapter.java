package com.baidu.duer.chinatalk_refactor.ui.game;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.baidu.duer.chinatalk_refactor.bean.game.Game;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;

import java.util.ArrayList;

public class GameFragmentAdapter extends QMUIPagerAdapter {

    private ArrayList<Game> gamesList;
    private Context context;

    public GameFragmentAdapter(Context context, ArrayList<Game> gamesList) {
        super();
        this.context = context;
        this.gamesList = gamesList;
    }

    @NonNull
    @Override
    protected Object hydrate(@NonNull ViewGroup container, int position) {
        return new GameFragment(context);
    }

    @Override
    protected void populate(@NonNull ViewGroup container, @NonNull Object item, int position) {
        GameFragment gameView = (GameFragment) item;
        gameView.setImageResource(gamesList.get(position).getImgResource());
        container.addView(gameView);
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
}
