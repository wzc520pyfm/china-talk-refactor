package com.baidu.duer.chinatalk_refactor.bean.question;

import androidx.annotation.Nullable;

public class QuestionsResult {

    @Nullable
    private GettedInQuestionsView success; // 要向UI公开的数据
    @Nullable
    private Integer error;

    public QuestionsResult(@Nullable Integer error) {
        this.error = error;
    }

    public QuestionsResult(@Nullable GettedInQuestionsView success) {
        this.success = success;
    }

    @Nullable
    public GettedInQuestionsView getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
