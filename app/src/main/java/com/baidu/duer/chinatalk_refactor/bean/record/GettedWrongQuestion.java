package com.baidu.duer.chinatalk_refactor.bean.record;

import com.baidu.duer.chinatalk_refactor.bean.exam.Exam;

import java.util.ArrayList;

public class GettedWrongQuestion {
    private ArrayList<WrongQuestionRecords> wrong;

    public ArrayList<WrongQuestionRecords> getWrong() {
        return wrong;
    }

    public void setWrong(ArrayList<WrongQuestionRecords> wrong) {
        this.wrong = wrong;
    }

    @Override
    public String toString() {
        return "GettedWrongQuestionRecords{" +
                "wrong=" + wrong +
                '}';
    }
}
