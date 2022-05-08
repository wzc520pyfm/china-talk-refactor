package com.baidu.duer.chinatalk_refactor.bean.exam;

public class Exam {
    private int examId; // 试卷id
    private String examName; // 试卷名
    private int total; // 总题数
    private int highestScore; // 最高分
    private int time; // 测试时长, 单位min

    public Exam(int examId, String examName, int total, int highestScore, int time) {
        this.examId = examId;
        this.examName = examName;
        this.total = total;
        this.highestScore = highestScore;
        this.time = time;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId=" + examId +
                ", examName='" + examName + '\'' +
                ", total=" + total +
                ", highestScore=" + highestScore +
                ", time=" + time +
                '}';
    }
}
