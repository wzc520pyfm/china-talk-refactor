package com.baidu.duer.chinatalk_refactor.bean.record;

import com.baidu.duer.chinatalk_refactor.bean.exam.Exam;
import com.baidu.duer.chinatalk_refactor.bean.question.Question;

public class WrongQuestionRecords {

    private int id;

    private String lastWrongAnswer;

    private Question question;

    private Exam examPaper;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastWrongAnswer() {
        return lastWrongAnswer;
    }

    public void setLastWrongAnswer(String lastWrongAnswer) {
        this.lastWrongAnswer = lastWrongAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Exam getExamPaper() {
        return examPaper;
    }

    public void setExamPaper(Exam exam) {
        this.examPaper = exam;
    }

    @Override
    public String toString() {
        return "WrongQuestionRecords{" +
                "id=" + id +
                ", lastWrongAnswer='" + lastWrongAnswer + '\'' +
                ", question=" + question +
                ", examPaper=" + examPaper +
                '}';
    }
}
