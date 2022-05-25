package com.baidu.duer.chinatalk_refactor.bean.exam;

import java.util.ArrayList;

/**
 * 数据类: 用于捕获获取的试卷(多张)
 */
public class GettedExams {

    private ArrayList<Exam> papers;

    public ArrayList<Exam> getPapers() {
        return papers;
    }

    public void setPapers(ArrayList<Exam> papers) {
        this.papers = papers;
    }

    @Override
    public String toString() {
        return "GettedExams{" +
                "papers=" + papers +
                '}';
    }
}
