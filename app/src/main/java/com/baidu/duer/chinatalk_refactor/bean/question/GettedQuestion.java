package com.baidu.duer.chinatalk_refactor.bean.question;

public class GettedQuestion {
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "GettedQuestion{" +
                "question=" + question +
                '}';
    }
}
