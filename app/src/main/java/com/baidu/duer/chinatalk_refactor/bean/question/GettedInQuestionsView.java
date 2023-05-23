package com.baidu.duer.chinatalk_refactor.bean.question;

import java.util.ArrayList;

public class GettedInQuestionsView {
    private ArrayList<Question> questions;

    public GettedInQuestionsView(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return "GettedInQuestionsView{" +
                "questions=" + questions +
                '}';
    }
}
