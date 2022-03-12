package com.baidu.duer.chinatalk_refactor.ui.game;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.duer.chinatalk_refactor.R;
import com.chenenyu.router.annotation.Route;

import android.os.Bundle;

@Route("game")
public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }
}