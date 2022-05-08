package com.baidu.duer.chinatalk_refactor.ui.wrong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.duer.chinatalk_refactor.R;
import com.chenenyu.router.annotation.Route;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route("wrong")
public class WrongActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pager)
    QMUIViewPager mViewPager;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong);
        ButterKnife.bind(this);

        mContext = this;
        initTopBar();
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