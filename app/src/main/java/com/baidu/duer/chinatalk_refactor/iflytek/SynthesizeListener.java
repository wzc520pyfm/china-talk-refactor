package com.baidu.duer.chinatalk_refactor.iflytek;

import com.iflytek.cloud.SpeechError;

/**
 * 合成回调
 */
public interface SynthesizeListener {

    void onError(SpeechError speechError);
}
