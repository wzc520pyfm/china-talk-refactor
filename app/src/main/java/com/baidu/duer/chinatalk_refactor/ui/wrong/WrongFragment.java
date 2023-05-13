package com.baidu.duer.chinatalk_refactor.ui.wrong;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.bean.exam.Exam;
import com.baidu.duer.chinatalk_refactor.bean.question.Question;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.ArrayList;
import java.util.Objects;

public class WrongFragment extends FrameLayout {
    private View root;
    private String lastWrongAnswer;
    private Integer position;
    Context mContext;

    public WrongFragment(Context context, Integer position) {
        super(context);
        this.mContext = context;
        this.position = position;

        root = LayoutInflater.from(context).inflate(R.layout.fragment_wrong,null, false);
        int width = QMUIDisplayHelper.dp2px(context, 400);
        int height = QMUIDisplayHelper.dp2px(context, 800);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
        lp.gravity = Gravity.CENTER;

        addView(root, lp);
    }

    public void setWrongAnswer(String lastWrongAnswer) {
        this.lastWrongAnswer = lastWrongAnswer;
    }

    public void setQuestion(Question question) {
        RadioGroup itemGroup = findViewById(R.id.item_group);
        TextView questionTv = findViewById(R.id.question);
        TextView rightAnswerTv = findViewById(R.id.right_answer);
        TextView analysisTv = findViewById(R.id.analysis);
        ArrayList<String> items = question.getSelectQuestion().getItems();
        String answer = question.getSelectQuestion().getAnswer();
        String analysis = question.getSelectQuestion().getAnalysis();
        questionTv.setText((position + 1) + ". " + question.getSelectQuestion().getContent());
        rightAnswerTv.setText(answer);
        analysisTv.setText(analysis);
        for(int i = 0; i < items.size(); i++) {
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,0,0,0);
            RadioButton radioButton = new RadioButton(getContext());
            // radioButton.setButtonDrawable(R.drawable.ic_circle);
            radioButton.setId(i); //必须要设置一个ID
            radioButton.setPadding(50, 0, 0, 0);
            radioButton.setMinWidth(1000);
            radioButton.setMinHeight(150);
            String content = question.getSelectQuestion().getItems().get(i);
            radioButton.setText(words[i] + content);
            radioButton.setTextColor(getResources().getColor(R.color.blackWord));
            if (Objects.equals(content, lastWrongAnswer)) {
                radioButton.setChecked(true);
            }
            itemGroup.addView(radioButton, lp);
        }
    }

    public void setExam(Exam exam) {
        TextView wrongSourceTv = findViewById(R.id.wrong_source);
        wrongSourceTv.setText(exam.getName());
    }

    private final String[] words = {"A、","B、","C、","D、","E、","F、","G、"};
}
