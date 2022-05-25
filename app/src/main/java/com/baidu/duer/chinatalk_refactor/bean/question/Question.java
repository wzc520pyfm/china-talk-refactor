package com.baidu.duer.chinatalk_refactor.bean.question;

import androidx.annotation.NonNull;

public class Question {

    @NonNull
    private int id; // 问题id
    private SelectQuestion selectQuestion; // 判断题
    private JudgmentQuestion judgmentQuestion; // 选择题
    private NarrateQuestion narrateQuestion; // 问答题

    public Question(int id, SelectQuestion selectQuestion) {
        this.id = id;
        this.selectQuestion = selectQuestion;
    }

    public Question(int id, JudgmentQuestion judgmentQuestion) {
        this.id = id;
        this.judgmentQuestion = judgmentQuestion;
    }

    public Question(int id, NarrateQuestion narrateQuestion) {
        this.id = id;
        this.narrateQuestion = narrateQuestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SelectQuestion getSelectQuestion() {
        return selectQuestion;
    }

    public void setSelectQuestion(SelectQuestion selectQuestion) {
        this.selectQuestion = selectQuestion;
    }

    public JudgmentQuestion getJudgmentQuestion() {
        return judgmentQuestion;
    }

    public void setJudgmentQuestion(JudgmentQuestion judgmentQuestion) {
        this.judgmentQuestion = judgmentQuestion;
    }

    public NarrateQuestion getNarrateQuestion() {
        return narrateQuestion;
    }

    public void setNarrateQuestion(NarrateQuestion narrateQuestion) {
        this.narrateQuestion = narrateQuestion;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", selectQuestion=" + selectQuestion +
                ", judgmentQuestion=" + judgmentQuestion +
                ", narrateQuestion=" + narrateQuestion +
                '}';
    }
}
