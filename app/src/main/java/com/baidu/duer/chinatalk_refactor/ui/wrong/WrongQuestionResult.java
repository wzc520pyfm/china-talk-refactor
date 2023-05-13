package com.baidu.duer.chinatalk_refactor.ui.wrong;

import androidx.annotation.Nullable;

import com.baidu.duer.chinatalk_refactor.ui.exam.GettedInExamsView;

public class WrongQuestionResult {
    @Nullable
    private GettedInWrongView success; // 要向UI公开的数据
    @Nullable
    private Integer error;

    WrongQuestionResult(@Nullable Integer error) {
        this.error = error;
    }

    WrongQuestionResult(@Nullable GettedInWrongView success) {
        this.success = success;
    }

    @Nullable
    GettedInWrongView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ExamResult{" +
                "success=" + success +
                ", error=" + error +
                '}';
    }
}
