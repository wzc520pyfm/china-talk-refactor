package com.baidu.duer.chinatalk_refactor.ui.exam;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.base.BaseRecyclerAdapter;
import com.baidu.duer.chinatalk_refactor.base.RecyclerViewHolder;
import com.baidu.duer.chinatalk_refactor.iflytek.SynthesizeListener;
import com.baidu.duer.chinatalk_refactor.iflytek.SynthesizeSpeechManager;
import com.chenenyu.router.Router;
import com.iflytek.cloud.SpeechError;
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ExamFragment extends Fragment {

    private Unbinder unbinder;
    private ExamViewModel dashboardViewModel;
    @BindView(R.id.pull_layout)
    QMUIPullLayout mPullLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private BaseRecyclerAdapter<String> mAdapter;

    public Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getContext();
        dashboardViewModel =
                ViewModelProviders.of(this).get(ExamViewModel.class);
        View root = inflater.inflate(R.layout.fragment_exam, container, false);
        unbinder = ButterKnife.bind(this,root);

        initData();

        return root;
    }

    private void initData() {

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
                }, 3000);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        mAdapter = new BaseRecyclerAdapter<String>(getContext(), null) {
            @Override
            public int getItemLayoutId(int viewType) {
                return android.R.layout.simple_list_item_1;
                // return R.layout.exam_list_item;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, int position, String item) {
                holder.setText(android.R.id.text1, item);
            }
        };
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                Toast.makeText(getContext(), "click position=" + pos, Toast.LENGTH_SHORT).show();
                Router.build("real").go(mContext);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        onDataLoaded();
    }

    private void onDataLoaded() {
        List<String> data = new ArrayList<>(Arrays.asList("Helps", "Maintain", "Liver", "Health", "Function", "Supports", "Healthy", "Fat",
                "Metabolism", "Nuturally", "Bracket", "Refrigerator", "Bathtub", "Wardrobe", "Comb", "Apron", "Carpet", "Bolster", "Pillow", "Cushion"));
        Collections.shuffle(data);
        mAdapter.setData(data);
    }

    /**
     * 上拉刷新
     */
    private void onRefreshData(){
        List<String> data = new ArrayList<>();
        long id = System.currentTimeMillis();
        for(int i = 0; i < 10; i++){
            data.add("onRefreshData-" + id + "-"+ i);
        }
        mAdapter.prepend(data);
        mRecyclerView.scrollToPosition(0);
    }

    /**
     * 下拉加载
     */
    private void onLoadMore(){
        List<String> data = new ArrayList<>();
        long id = System.currentTimeMillis();
        for(int i = 0; i < 10; i++){
            data.add("onLoadMore-" + id + "-"+ i);
        }
        mAdapter.append(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder != null) {
            unbinder.unbind();//视图销毁时必须解绑
        }
    }

}