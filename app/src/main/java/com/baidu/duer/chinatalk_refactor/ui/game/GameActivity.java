package com.baidu.duer.chinatalk_refactor.ui.game;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.animation.CardTransformer;
import com.baidu.duer.chinatalk_refactor.bean.game.Game;
import com.baidu.duer.chinatalk_refactor.iflytek.RecognizeListener;
import com.baidu.duer.chinatalk_refactor.iflytek.RecognizeSpeechManager;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.iflytek.cloud.SpeechError;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;

import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route("game")
public class GameActivity extends AppCompatActivity implements RecognizeListener {

    @BindView(R.id.pager)
    QMUIViewPager mViewPager;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    private QMUIPopup mNormalPopup;
    // 记录当前pager的index
    private int mCurrentPosition;
    GameFragmentAdapter pagerAdapter;

    public Context mContext;

    private ArrayList<Game> gamesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        mContext = this;
        initTopBar();
        initData(5);
        initPagers();

        RecognizeSpeechManager.instance().init(mContext);
        RecognizeSpeechManager.instance().setRecognizeListener(this);
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTopBar.setTitle(R.string.game);
        mTopBar.addRightImageButton(R.drawable.ic_baseline_tip, QMUIViewHelper.generateViewId())
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showTip(v);
                    }
                });
    }

    private void initData(int count) {

        for (int i = 0; i < count; i++) {
            gamesList.add(new Game(R.drawable.example));
        }

    }

    @OnClick(R.id.collection)
    public void onClick(View v) {
        if(v.isSelected()) {
            v.setSelected(false);
        } else {
            v.setSelected(true);
        }
    }

    private void initPagers() {
        pagerAdapter = new GameFragmentAdapter(mContext, gamesList);
        //setPageTransformer默认采用ViewCompat.LAYER_TYPE_HARDWARE， 但它在某些4.x的国产机下会crash
        boolean canUseHardware = Build.VERSION.SDK_INT >= 21;
        mViewPager.setPageTransformer(false, new CardTransformer(),
                canUseHardware ? ViewCompat.LAYER_TYPE_HARDWARE : ViewCompat.LAYER_TYPE_SOFTWARE);
        mViewPager.setInfiniteRatio(500);
        // mViewPager.setEnableLoop(true);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    void showTip(View v) {
        TextView textView = new TextView(mContext);
        textView.setLineSpacing(QMUIDisplayHelper.dp2px(mContext, 4), 1.0f);
        int padding = QMUIDisplayHelper.dp2px(mContext, 20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(R.string.game_warm_tip);
        textView.setTextColor(
                QMUIResHelper.getAttrColor(mContext, R.attr.app_skin_common_title_text_color));
        QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
        builder.textColor(R.attr.app_skin_common_title_text_color);
        QMUISkinHelper.setSkinValue(textView, builder);
        builder.release();
        mNormalPopup = QMUIPopups.popup(mContext, QMUIDisplayHelper.dp2px(mContext, 250))
                .preferredDirection(QMUIPopup.DIRECTION_CENTER_IN_SCREEN) // 在页面中间展示
                .view(textView)
                .skinManager(QMUISkinManager.defaultInstance(mContext))
                .edgeProtection(QMUIDisplayHelper.dp2px(mContext, 20))
                .dimAmount(0.6f)
                .offsetX(QMUIDisplayHelper.dp2px(mContext, 20))
                .offsetYIfBottom(QMUIDisplayHelper.dp2px(mContext, 5))
                .shadow(true)
                .arrow(true)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .onDismiss(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Toast.makeText(mContext, "onDismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .show(v);
    }

    @Override
    public void onNewResult(String result) {
        // 获取当前页面, 并设置语音识别结果
        View view = pagerAdapter.getItemAt(mCurrentPosition);
        EditText et = view.findViewById(R.id.gameAnswerET);
        et.setText(result);
    }

    @Override
    public void onTotalResult(String result, boolean isLast) {

    }

    @Override
    public void onError(SpeechError speechError) {

    }
}