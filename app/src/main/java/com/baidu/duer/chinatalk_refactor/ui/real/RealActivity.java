package com.baidu.duer.chinatalk_refactor.ui.real;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.animation.CardTransformer;
import com.baidu.duer.chinatalk_refactor.bean.game.Game;
import com.baidu.duer.chinatalk_refactor.bean.real.RealQuestion;
import com.baidu.duer.chinatalk_refactor.ui.game.GameFragmentAdapter;
import com.chenenyu.router.annotation.Route;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route("real")
public class RealActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pager)
    QMUIViewPager mViewPager;

    Context mContext;
    private ArrayList<RealQuestion> realQuestionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real);
        ButterKnife.bind(this);

        mContext = this;
        initTopBar();
        initData(5);
        initPagers();
    }

    /**
     * 初始化状态栏
     */
    private void initTopBar() {
        mTopBar.setTitleGravity(Gravity.LEFT); // 左对齐
        mTopBar.setTitle("Real exam paper(1)");
        mTopBar.setBorderColor(getColor(R.color.color_theme_blue));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.exam_time, null); // 获取view
        mTopBar.addRightView(view, QMUIViewHelper.generateViewId()); // 将view绑定到topbar
        TextView tv = mTopBar.findViewById(R.id.time);
        tv.setText("lalala"); // 重设view内容
    }

    private void initData(int count) {

        for (int i = 0; i < count; i++) {
            ArrayList<String> items = new ArrayList<>();
            items.add("选项"+i);
            items.add("选项"+i+1);
            items.add("选项"+i+2);
            realQuestionsList.add(new RealQuestion(i, "题目题目踢踢踢踢踢踢ititit以体态题目题目踢踢踢踢踢踢ititit以体态题目题目踢踢踢踢踢踢ititit以体态题目题目踢踢踢踢踢踢ititit以体态题目题目踢踢踢踢踢踢ititit以体态题目题目踢踢踢踢踢踢ititit以体态题目题目踢踢踢踢踢踢ititit以体态"+i, items, "选项2"));
        }

    }

    private void initPagers() {
        RealFragmentAdapter pagerAdapter = new RealFragmentAdapter(mContext, realQuestionsList);
        //setPageTransformer默认采用ViewCompat.LAYER_TYPE_HARDWARE， 但它在某些4.x的国产机下会crash
        boolean canUseHardware = Build.VERSION.SDK_INT >= 21;
        mViewPager.setPageTransformer(false, new CardTransformer(),
                canUseHardware ? ViewCompat.LAYER_TYPE_HARDWARE : ViewCompat.LAYER_TYPE_SOFTWARE);
        mViewPager.setInfiniteRatio(500);
        // mViewPager.setEnableLoop(true);
        mViewPager.setAdapter(pagerAdapter);
    }
}