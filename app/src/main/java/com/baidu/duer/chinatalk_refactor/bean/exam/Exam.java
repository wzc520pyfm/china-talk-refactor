package com.baidu.duer.chinatalk_refactor.bean.exam;

import androidx.annotation.NonNull;

import com.baidu.duer.chinatalk_refactor.bean.record.GradeRecords;

import java.util.ArrayList;

/**
 * 试卷实体
 */
public class Exam {
    @NonNull
    private int id; // 试卷id
    @NonNull
    private String name; // 试卷名
    @NonNull
    private String type; // 试卷分类
    private String description; // 描述
    private int total; // 总题数
    private int totalScore; // 总分
    private int timeLimit; // 测试时长, 单位min
    private ArrayList<Score> scorePapers; // 题目分值
    private ArrayList<GradeRecords> gradeRecords; // 相关的最高分记录(一般接口返回当前用户的最高分记录)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public ArrayList<Score> getScorePapers() {
        return scorePapers;
    }

    public void setScorePapers(ArrayList<Score> scorePapers) {
        this.scorePapers = scorePapers;
    }

    public ArrayList<GradeRecords> getGradeRecords() {
        return gradeRecords;
    }

    public void setGradeRecords(ArrayList<GradeRecords> gradeRecords) {
        this.gradeRecords = gradeRecords;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", total=" + total +
                ", totalScore=" + totalScore +
                ", timeLimit=" + timeLimit +
                ", scorePapers=" + scorePapers +
                ", gradeRecords=" + gradeRecords +
                '}';
    }
}
