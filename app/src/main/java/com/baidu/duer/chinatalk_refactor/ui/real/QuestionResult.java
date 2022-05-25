package com.baidu.duer.chinatalk_refactor.ui.real;

import androidx.annotation.Nullable;

/**
 * 此类存储获取题目的结果: 成功(题目信息)或错误信息
 */
public class QuestionResult {
    @Nullable
    private GettedInQuestionView success; // 要向UI公开的数据
    @Nullable
    private Integer error;

    QuestionResult(@Nullable Integer error) {
        this.error = error;
    }

    QuestionResult(@Nullable GettedInQuestionView success) {
        this.success = success;
    }

    @Nullable
    GettedInQuestionView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
