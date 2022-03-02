package com.baidu.duer.chinatalk_refactor.iflytek;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * 语音合成
 */
public class SynthesizeSpeechManager implements SynthesizerListener, InitListener {

    private static final String TAG = "SynthesizeSpeechManager";

    // 默认发音人
    private String voicer = "xiaoyan";

    // 结果回调对象
    private SynthesizeListener synthesizeListener;

    // 语音合成对象
    private SpeechSynthesizer tts;

    // 上下文的弱引用,以便在不使用时回收,避免内存泄露
    private WeakReference<Context> bindContext;

    // 单例
    private static SynthesizeSpeechManager instance;

    private SynthesizeSpeechManager() {
    }

    /**
     * 单例方法
     */
    public static SynthesizeSpeechManager instance() {
        if (instance == null) {
            instance = new SynthesizeSpeechManager();
        }
        return instance;
    }

    /**
     * 设置结果回调对象
     */
    public void setSynthesizeListener(SynthesizeListener synthesizeListener) {
        this.synthesizeListener = synthesizeListener;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        if (bindContext == null) {
            bindContext = new WeakReference<Context>(context);
        }
        if (tts == null) {
            tts = SpeechSynthesizer.createSynthesizer(bindContext.get(), this);
        }
    }

    @Override
    public void onInit(int code) {
        if (code != ErrorCode.SUCCESS) {
            Log.d(TAG, "init error code " + code);
        }
    }

    // 接着需要实现自定义接口的三个方法

    /**
     * 开始合成
     */
    public int startSpeak(String texts) {
        setParam();
        return tts.startSpeaking(texts, this);
    }

    /**
     * 取消合成
     */
    public void stopSpeak() {
        tts.stopSpeaking();
    }

    /**
     * 暂停播放
     */
    public void pauseSpeak() {
        tts.pauseSpeaking();
    }

    /**
     * 继续播放
     */
    public void resumeSpeak() {
        tts.resumeSpeaking();
    }

    /**
     * 垃圾回收
     */
    public void release() {
        tts.stopSpeaking();
        tts.destroy();
        // tts = null;
        bindContext.clear();
        // bindContext = null;
    }

    @Override
    public void onSpeakBegin() {
        Log.d(TAG, "开始播放");
    }

    @Override
    public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        Log.d(TAG, "合成进度: percent =" + percent);
    }

    @Override
    public void onSpeakPaused() {
        Log.d(TAG, "暂停播放");
    }

    @Override
    public void onSpeakResumed() {
        Log.d(TAG, "继续播放");
    }

    @Override
    public void onSpeakProgress(int percent, int beginPos, int endPos) {
        Log.e(TAG, "播放进度: percent =" + percent);
    }

    @Override
    public void onCompleted(SpeechError speechError) {
        Log.d(TAG, "播放完成");
        if (speechError != null) {
            Log.d(TAG, speechError.getPlainDescription(true));
            synthesizeListener.onError(speechError);
        }
    }

    @Override
    public void onEvent(int eventType, int arg1, int arg2, Bundle bundle) {
        //	 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
        if(bundle != null) {
            Log.d(TAG, "session id =" + bundle.getString(SpeechEvent.KEY_EVENT_SESSION_ID));
            Log.e(TAG, "EVENT_TTS_BUFFER = " + Objects.requireNonNull(bundle.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER)).length);
        }
    }

    /**
     * 参数设置
     */
    private void setParam() {
        // 清空参数
        tts.setParameter(SpeechConstant.PARAMS, null);
        // 设置合成引擎
        tts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 支持实时音频返回，仅在 synthesizeToUri 条件下支持
        tts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
        //	mTts.setParameter(SpeechConstant.TTS_BUFFER_TIME,"1");
        // 设置在线合成发音人
        tts.setParameter(SpeechConstant.VOICE_NAME, voicer);
        //设置合成语速
        tts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        tts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        tts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        tts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        tts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        /* mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,
                getExternalFilesDir("msc").getAbsolutePath() + "/tts.pcm"); */
    }
}
