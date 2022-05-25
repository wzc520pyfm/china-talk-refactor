package com.baidu.duer.chinatalk_refactor.bean.question;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SelectQuestion {

    @NonNull
    private int id; // 选择题id
    @NonNull
    private String questionClassification; // 分类
    @NonNull
    private String questionDifficulty; // 难度
    @NonNull
    private String content; // 题目内容
    @NonNull
    private String option1; // 选项1
    @NonNull
    private String option2; // 选项2
    private String option3; // 选项3
    private String option4; // 选项4
    private String option5; // 选项5
    private String option6; // 选项6
    @NonNull
    private String answer; // 答案
    private String tip; // 提示
    private String analysis; // 解析

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionClassification() {
        return questionClassification;
    }

    public void setQuestionClassification(String questionClassification) {
        this.questionClassification = questionClassification;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getOption5() {
        return option5;
    }

    public void setOption5(String option5) {
        this.option5 = option5;
    }

    public String getOption6() {
        return option6;
    }

    public void setOption6(String option6) {
        this.option6 = option6;
    }

    public ArrayList<String> getItems() {
        ArrayList<String> items = new ArrayList<>();
        items.add(getOption1());
        items.add(getOption2());
        if(getOption3() != null) {
            items.add(getOption3());
        }
        if(getOption4() != null) {
            items.add(getOption4());
        }
        if(getOption5() != null) {
            items.add(getOption5());
        }
        if(getOption6() != null) {
            items.add(getOption6());
        }
        return items;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    @Override
    public String toString() {
        return "SelectQuestion{" +
                "id=" + id +
                ", questionClassification='" + questionClassification + '\'' +
                ", questionDifficulty='" + questionDifficulty + '\'' +
                ", content='" + content + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", option4='" + option4 + '\'' +
                ", option5='" + option5 + '\'' +
                ", option6='" + option6 + '\'' +
                ", answer='" + answer + '\'' +
                ", tip='" + tip + '\'' +
                ", analysis='" + analysis + '\'' +
                '}';
    }
}
