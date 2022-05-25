package com.baidu.duer.chinatalk_refactor.ui.real;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.animation.CardTransformer;
import com.baidu.duer.chinatalk_refactor.bean.ServiceResponse;
import com.baidu.duer.chinatalk_refactor.bean.exam.Exam;
import com.baidu.duer.chinatalk_refactor.bean.exam.UserAnswerResult;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;


import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;
import retrofit2.http.HTTP;

@Route("real")
public class RealActivity extends AppCompatActivity {

    private RealViewModel realViewModel;
    @InjectParam(key = "id")
    Integer paperId; // 参数注入, 获取其他页面传来的值
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pager)
    QMUIViewPager mViewPager;

    private RealFragmentAdapter pagerAdapter;
    private int mCurrentPosition = 0;// 记录当前pager的index

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real);
        ButterKnife.bind(this);
        Router.injectParams(this); // 实现参数注入
        mContext = this;
        realViewModel =
                ViewModelProviders.of(this, new RealViewModelFactory(this.getApplication(), paperId)).get(RealViewModel.class);
        realViewModel.getExamResult().observe(this, new Observer<ExamResult>() {
            @Override
            public void onChanged(ExamResult examResult) {
                initPagers(examResult.getSuccess().getExam());
            }
        });
        realViewModel.getUserAnswerResult().observe(this, new Observer<UserAnswerResult>() {
            @Override
            public void onChanged(UserAnswerResult userAnswerResult) {
                showScore(userAnswerResult);
            }
        });
        realViewModel.getSubmitResult().observe(this, new Observer<ServiceResponse>() {
            @Override
            public void onChanged(ServiceResponse serviceResponse) {
                if(serviceResponse.getCode() != 200) {
                    showError();
                }
            }
        });
        initTopBar();
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
        tv.setText("00:20:30"); // 重设view内容
    }


    private void initPagers(Exam exam) {
        pagerAdapter = new RealFragmentAdapter(mContext, exam.getScorePapers());
        //setPageTransformer默认采用ViewCompat.LAYER_TYPE_HARDWARE， 但它在某些4.x的国产机下会crash
        boolean canUseHardware = Build.VERSION.SDK_INT >= 21;
        mViewPager.setPageTransformer(false, new CardTransformer(),
                canUseHardware ? ViewCompat.LAYER_TYPE_HARDWARE : ViewCompat.LAYER_TYPE_SOFTWARE);
        mViewPager.setInfiniteRatio(500);
        // mViewPager.setEnableLoop(true);
        mViewPager.setAdapter(pagerAdapter);
        // TODO 第一个fragment不会触发OnPageChange事件, 故需要在此手动为其添加radioGroup选择事件
        View view = pagerAdapter.getItemAt(mCurrentPosition);
        RadioGroup itemGroup = view.findViewById(R.id.item_group);
        itemGroup.setOnCheckedChangeListener(new RadioListener());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                // 获取当前fragment
                View view = pagerAdapter.getItemAt(position);
                RadioGroup itemGroup = view.findViewById(R.id.item_group);
                itemGroup.setOnCheckedChangeListener(new RadioListener());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.submitBtn)
    public void onClick(View v) {
        if(realViewModel.isAnswered()) {
            realViewModel.computeScore();
            realViewModel.submitScore();
        } else {
            new QMUIDialog.MessageDialogBuilder(this)
                    .setTitle("标题")
                    .setMessage("您有题目未作答，确定要交卷吗？")
                    .addAction("取消", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    })
                    .addAction("确定", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            realViewModel.computeScore();
                            realViewModel.submitScore();
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    private void showScore(UserAnswerResult userAnswerResult) {
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("恭喜")
                .setMessage("您本次得分是" + userAnswerResult.getScore() + "/" + userAnswerResult.getTotalScore() + "!" )
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showError() {
        final QMUITipDialog tipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord("成绩保存失败")
                .create();
        tipDialog.show();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1500);
    }

    class RadioListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            String answer = pagerAdapter.getFragmentItemAt(mCurrentPosition).getAnswer();
            Integer questionId = pagerAdapter.getFragmentItemAt(mCurrentPosition).getQuestionId();
            RadioButton radioButton = group.findViewById(group.getCheckedRadioButtonId());
            String userAnswer = radioButton.getText().toString().split("[A-Z]、")[1];
            realViewModel.setUserAnswer(mCurrentPosition, questionId, userAnswer, userAnswer.equals(answer));
        }
    }
}