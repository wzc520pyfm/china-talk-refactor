package com.baidu.duer.chinatalk_refactor.iflytek;

import com.iflytek.cloud.SpeechError;

public interface EvaluateListener {

    void onNewResult(String result);

    void onTotalResult(String result,boolean isLast);

    void onError(SpeechError speechError);
}
