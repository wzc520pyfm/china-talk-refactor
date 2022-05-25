package com.baidu.duer.chinatalk_refactor.bean.exam;

import androidx.annotation.NonNull;

public class UserAnswerResult {
    @NonNull
    private int examPaperId; // 试卷id
    @NonNull
    private int totalScore; // 总分
    @NonNull
    private int score; // 得分

    public UserAnswerResult(int examPaperId, int totalScore, int score) {
        this.examPaperId = examPaperId;
        this.totalScore = totalScore;
        this.score = score;
    }

    public int getExamPaperId() {
        return examPaperId;
    }

    public void setExamPaperId(int examPaperId) {
        this.examPaperId = examPaperId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "UserAnswerResult{" +
                "examPaperId=" + examPaperId +
                ", totalScore=" + totalScore +
                ", score=" + score +
                '}';
    }
}
