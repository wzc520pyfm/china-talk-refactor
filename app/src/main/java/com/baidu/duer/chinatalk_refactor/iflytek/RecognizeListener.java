package com.baidu.duer.chinatalk_refactor.iflytek;

import com.iflytek.cloud.SpeechError;

/**
 * 听写回调
 */
public interface RecognizeListener {

    void onNewResult(String result);

    void onTotalResult(String result,boolean isLast);

    void onError(SpeechError speechError);
}