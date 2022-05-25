package com.baidu.duer.chinatalk_refactor.ui.exam;

import com.baidu.duer.chinatalk_refactor.bean.exam.Exam;

import java.util.ArrayList;

/**
 * 此类将经过处理的ExamPaper信息公开给UI
 */
public class GettedInExamsView {
    private ArrayList<Exam> exams;

    public GettedInExamsView(ArrayList<Exam> exams) {
        this.exams = exams;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    @Override
    public String toString() {
        return "GettedInExamView{" +
                "exams=" + exams +
                '}';
    }
}
