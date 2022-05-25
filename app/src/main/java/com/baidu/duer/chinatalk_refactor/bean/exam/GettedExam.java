package com.baidu.duer.chinatalk_refactor.bean.exam;

/**
 * 数据类: 用于捕获获取的试卷(指定的一张)
 */
public class GettedExam {

    private Exam paper;

    public Exam getPaper() {
        return paper;
    }

    public void setPaper(Exam paper) {
        this.paper = paper;
    }

    @Override
    public String toString() {
        return "GettedExam{" +
                "paper=" + paper +
                '}';
    }
}
