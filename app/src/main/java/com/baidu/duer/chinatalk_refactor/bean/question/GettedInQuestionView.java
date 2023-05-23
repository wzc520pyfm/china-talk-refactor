package com.baidu.duer.chinatalk_refactor.bean.question;

import com.baidu.duer.chinatalk_refactor.bean.question.Question;

/**
 * 此类将经过处理的真题测试卷信息公开给UI
 */
public class GettedInQuestionView {
    private Question question;

    public GettedInQuestionView(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    @Override
    public String toString() {
        return "GettedInQuestionView{" +
                "question=" + question +
                '}';
    }
}
