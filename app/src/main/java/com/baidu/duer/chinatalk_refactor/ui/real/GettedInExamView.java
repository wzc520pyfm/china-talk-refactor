package com.baidu.duer.chinatalk_refactor.ui.real;

import com.baidu.duer.chinatalk_refactor.bean.exam.Exam;

/**
 * 此类将经过处理的真题测试卷信息公开给UI
 */
public class GettedInExamView {
    private Exam exam;

    public GettedInExamView(Exam exam) {
        this.exam = exam;
    }

    public Exam getExam() {
        return exam;
    }

    @Override
    public String toString() {
        return "GettedInRealExamView{" +
                "exam=" + exam +
                '}';
    }
}
