package com.baidu.duer.chinatalk_refactor.bean.question;

import java.util.ArrayList;

public class GettedQuestions {

    private ArrayList<Question> list;

    public ArrayList<Question> getQuestions() {
        return list;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.list = questions;
    }

    @Override
    public String toString() {
        return "GettedQuestions{" +
                "list=" + list +
                '}';
    }
}
