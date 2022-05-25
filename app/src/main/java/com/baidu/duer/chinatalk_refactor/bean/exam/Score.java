package com.baidu.duer.chinatalk_refactor.bean.exam;

import androidx.annotation.NonNull;

import com.baidu.duer.chinatalk_refactor.bean.question.Question;

/**
 * 分数记录, 记录对应题目的分值
 */
public class Score {

    @NonNull
    private int id; // 分数记录id
    @NonNull
    private int score; // 分数
    @NonNull
    private Question question; // 问题(题目)

    public Score(int id, int score, Question question) {
        this.id = id;
        this.score = score;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", score=" + score +
                ", question=" + question +
                '}';
    }
}
