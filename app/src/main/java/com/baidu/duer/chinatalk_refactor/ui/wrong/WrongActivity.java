package com.baidu.duer.chinatalk_refactor.ui.wrong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.animation.CardTransformer;
import com.baidu.duer.chinatalk_refactor.bean.exam.Exam;
import com.baidu.duer.chinatalk_refactor.bean.record.WrongQuestionRecords;
import com.baidu.duer.chinatalk_refactor.ui.real.ExamResult;
import com.baidu.duer.chinatalk_refactor.ui.real.RealActivity;
import com.baidu.duer.chinatalk_refactor.ui.real.RealFragmentAdapter;
import com.baidu.duer.chinatalk_refactor.ui.real.RealViewModel;
import com.baidu.duer.chinatalk_refactor.ui.real.RealViewModelFactory;
import com.chenenyu.router.annotation.Route;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route("wrong")
public class WrongActivity extends AppCompatActivity {

    private WrongViewModel wrongViewModel;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pager)
    QMUIViewPager mViewPager;

    private WrongFragmentAdapter pagerAdapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong);
        ButterKnife.bind(this);
        mContext = this;
        wrongViewModel =
                ViewModelProviders.of(this).get(WrongViewModel.class);
        wrongViewModel.getWrongQuestionResult().observe(this, new Observer<WrongQuestionResult>() {
            @Override
            public void onChanged(WrongQuestionResult wrongQuestionResult) {
                initPagers(wrongQuestionResult.getSuccess().getWrongQuestions());
            }
        });
        initTopBar();
    }

    private void initPagers(ArrayList<WrongQuestionRecords> wrongQuestionList) {
        pagerAdapter = new WrongFragmentAdapter(mContext, wrongQuestionList);
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

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化状态栏
     */
    private void initTopBar() {
        mTopBar.setTitleGravity(Gravity.LEFT); // 左对齐
        mTopBar.setTitle("Wrong topic");
        mTopBar.setBorderColor(getColor(R.color.color_theme_blue));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}