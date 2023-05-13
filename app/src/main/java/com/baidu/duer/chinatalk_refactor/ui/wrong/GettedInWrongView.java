package com.baidu.duer.chinatalk_refactor.ui.wrong;

import com.baidu.duer.chinatalk_refactor.bean.record.WrongQuestionRecords;

import java.util.ArrayList;

public class GettedInWrongView {
    private ArrayList<WrongQuestionRecords> wrongQuestionList;

    public GettedInWrongView(ArrayList<WrongQuestionRecords> wrongQuestionList) {
        this.wrongQuestionList = wrongQuestionList;
    }

    public ArrayList<WrongQuestionRecords> getWrongQuestions() {
        return wrongQuestionList;
    }

    @Override
    public String toString() {
        return "GettedInExamView{" +
                "exams=" + wrongQuestionList +
                '}';
    }
}
