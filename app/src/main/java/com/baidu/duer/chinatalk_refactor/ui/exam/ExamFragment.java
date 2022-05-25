package com.baidu.duer.chinatalk_refactor.ui.exam;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.base.BaseRecyclerAdapter;
import com.baidu.duer.chinatalk_refactor.base.RecyclerViewHolder;
import com.baidu.duer.chinatalk_refactor.bean.exam.Exam;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route("exams")
public class ExamFragment extends Fragment {

    private Unbinder unbinder;
    private ExamViewModel examViewModel;
    @BindView(R.id.pull_layout)
    QMUIPullLayout mPullLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private BaseRecyclerAdapter<Exam> mAdapter;

    public Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getContext();
        examViewModel =
                ViewModelProviders.of(this).get(ExamViewModel.class);
        View root = inflater.inflate(R.layout.fragment_exam, container, false);
        unbinder = ButterKnife.bind(this,root);
        examViewModel.getExamResult().observe(getViewLifecycleOwner(), new Observer<ExamsResult>() {
            @Override
            public void onChanged(ExamsResult examResult) {
                if (examResult.getError() != null) { // 展示出错信息
                    showLoginFailed(examResult.getError());
                }
                if (examResult.getSuccess() != null) { // 数据获取成功,将数据公开给UI,UI拿到数据执行一些操作
                    updateUiWithUser(examResult.getSuccess());
                }
            }
        });

        initData();

        return root;
    }

    /**
     * 试卷获取成功,拿到试卷信息,执行后续操作
     */
    private void updateUiWithUser(GettedInExamsView model) {
        Collections.shuffle(model.getExams());
        mAdapter.setData(model.getExams());
    }

    private void initData() {
        // 监听上拉和下拉动作
        mPullLayout.setActionListener(new QMUIPullLayout.ActionListener() {
            @Override
            public void onActionTriggered(@NonNull QMUIPullLayout.PullAction pullAction) {
                mPullLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_TOP){
                            onRefreshData();
                        }else if(pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_BOTTOM){
                            onLoadMore();
                        }
                        mPullLayout.finishActionRun(pullAction);
                    }
                }, 1000);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        mAdapter = new BaseRecyclerAdapter<Exam>(getContext(), null) {
            /**
             * 获取视图
             */
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.exam_list_item;
            }

            /**
             * 处理视图
             */
            @Override
            public void bindData(RecyclerViewHolder holder, int position, Exam item) {
                holder.setText(R.id.exam_title, item.getName());
                holder.setText(R.id.total, ""+item.getTotal());
                if(item.getGradeRecords().size() > 0) {
                    holder.setText(R.id.highest_score, ""+item.getGradeRecords().get(0).getScore());
                } else {
                    holder.setText(R.id.highest_score, "0");
                }
                holder.setText(R.id.exam_time, ""+item.getTimeLimit());
                QMUILinearLayout examBg = (QMUILinearLayout)holder.getView(R.id.exam_card);
                QMUIRoundButton btnStart = (QMUIRoundButton)holder.getView(R.id.btStart);
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", item.getId());
                        Router.build("real").with(bundle).go(mContext);
                    }
                });
                examBg.setShadowColor(0xff0000ff); // 阴影色
                examBg.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 15),
                        QMUIDisplayHelper.dp2px(getActivity(), 5), 0.2f);
            }
        };
        /**
         * 监听点击事件
         */
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                Toast.makeText(getContext(), "click position=" + pos, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 上拉刷新
     */
    private void onRefreshData(){
//        mAdapter.prepend(examViewModel.onRefreshData());
//        mRecyclerView.scrollToPosition(0);
    }

    /**
     * 下拉加载
     */
    private void onLoadMore(){
//        mAdapter.append(examViewModel.onLoadMore());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder != null) {
            unbinder.unbind();//视图销毁时必须解绑
        }
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(mContext, errorString, Toast.LENGTH_SHORT).show();
    }

}