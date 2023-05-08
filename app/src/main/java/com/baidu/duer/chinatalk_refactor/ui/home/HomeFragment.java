package com.baidu.duer.chinatalk_refactor.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.baidu.duer.chinatalk_refactor.R;
import com.chenenyu.router.Router;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {
    //UI视图的展示和事件包含在Fragment或Activity中

    private Unbinder unbinder;
    private HomeViewModel homeViewModel;
    @BindView(R.id.search_input)
    QMUILinearLayout searchInput;
    @BindView(R.id.study_plain)
    QMUILinearLayout studyPlain;
    @BindView(R.id.wrong_topic)
    QMUILinearLayout wrongTopic;
    @BindView(R.id.editTextSearch)
    EditText editTextSearch;

    public Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getContext();
        //构建ViewModel实例
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        //创建视图对象
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // fragment绑定butterKnife
        unbinder = ButterKnife.bind(this,root);

        initStyle();

        //返回视图对象
        return root;
    }

    void initStyle() {
        searchInput.setShadowColor(0xff0000ff); // 阴影色
        // searchInput.setRadius(QMUIDisplayHelper.dp2px(mContext, 15)); // 设置圆角
        searchInput.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 15),
                QMUIDisplayHelper.dp2px(getActivity(), 14), 0.25f); // 设置圆角、模糊（发散）、色彩深度
        studyPlain.setShadowColor(0xff0000ff); // 阴影色
        studyPlain.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 15),
                QMUIDisplayHelper.dp2px(getActivity(), 10), 0.4f);
        wrongTopic.setShadowColor(0xff0000ff); // 阴影色
        wrongTopic.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 15),
                QMUIDisplayHelper.dp2px(getActivity(), 10), 0.4f);
    }

    @OnClick(R.id.wrong_topic)
    public void onClick1() {
        Router.build("wrong").go(this);
    }
    @OnClick(R.id.game_spoken)
    public void onClick2() {
        Router.build("gameList").go(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder != null) {
            unbinder.unbind();//视图销毁时必须解绑
        }
    }
}