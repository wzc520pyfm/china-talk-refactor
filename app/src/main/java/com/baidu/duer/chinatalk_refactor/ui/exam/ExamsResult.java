package com.baidu.duer.chinatalk_refactor.ui.exam;

import androidx.annotation.Nullable;

/**
 * 此类存储获取试卷的结果: 成功(试卷信息)或错误信息
 */
public class ExamsResult {
    @Nullable
    private GettedInExamsView success; // 要向UI公开的数据
    @Nullable
    private Integer error;

    ExamsResult(@Nullable Integer error) {
        this.error = error;
    }

    ExamsResult(@Nullable GettedInExamsView success) {
        this.success = success;
    }

    @Nullable
    GettedInExamsView getSuccess() {
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
