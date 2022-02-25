package com.baidu.duer.chinatalk_refactor.iflytek;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.baidu.duer.chinatalk_refactor.utils.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * 音频读写转换
 */
public class RecognizeSpeechManager implements RecognizerListener, InitListener {

    private static final String TAG = "RecognizeSpeechManager";

    // 结果回调对象
    private RecognizeListener recognizeListener;

    // 语音听写对象
    private SpeechRecognizer iat;

    private StringBuffer charBufffer = new StringBuffer();

    // 上下文的弱引用,以便在不使用时回收,避免内存泄露 (当一个对象仅仅被弱引用指向,而没有其他强引用指向时,在下一次gc运行时将会被回收)
    private WeakReference<Context> bindContext;

    // 单例
    private static RecognizeSpeechManager instance;

    private RecognizeSpeechManager() {
    }

    /**
     * 单例方法
     */
    public static RecognizeSpeechManager instance() {
        if (instance == null) {
            instance = new RecognizeSpeechManager();
        }
        return instance;
    }

    /**
     * 设置结果回调对象
     */
    public void setRecognizeListener(RecognizeListener recognizeListener) {
        this.recognizeListener = recognizeListener;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        if (bindContext == null) {
            bindContext = new WeakReference<Context>(context);
        }
        if (iat == null) {
            iat = SpeechRecognizer.createRecognizer(bindContext.get(), this);
        }
    }

    @Override
    public void onInit(int code) {
        if (code != ErrorCode.SUCCESS) {
            Log.d(TAG, "init error code " + code);
        }
    }

    /**
     * 开始监听
     * ErrorCode.SUCCESS 监听成功状态码
     */
    public int startRecognize() {
        setParam();
        return iat.startListening(this);
    }

    /**
     * 取消听写
     */
    public void cancelRecognize() {
        iat.cancel();
    }

    /**
     * 停止听写
     */
    public void stopRecognize() {
        iat.stopListening();
    }

    public void release() {
        iat.cancel();
        iat.destroy();
//        iat = null;
        bindContext.clear();
        bindContext = null;
        charBufffer.delete(0, charBufffer.length());
    }

    @Override
    public void onVolumeChanged(int i, byte[] bytes) {

    }

    @Override
    public void onBeginOfSpeech() {
        Log.d(TAG, "onBeginOfSpeech");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech isListening " + iat.isListening());
    }

    @Override
    public void onResult(RecognizerResult results, boolean b) {
        if (recognizeListener != null) {
            recognizeListener.onNewResult(printResult(results));
            recognizeListener.onTotalResult(charBufffer.toString(), iat.isListening());
        }
    }


    @Override
    public void onError(SpeechError speechError) {
        if (recognizeListener != null) {
            recognizeListener.onError(speechError);
        }
    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {
        Log.d(TAG, "onEvent type " + i);
    }


    private String printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());
        Log.d(TAG, "printResult " + text + " isListening " + iat.isListening());
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(text)) {
            charBufffer.append(text);
        }
        return text;
    }

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        iat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        iat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        iat.setParameter(SpeechConstant.RESULT_TYPE, "json");


        iat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        iat.setParameter(SpeechConstant.ACCENT, "mandarin");

        //此处用于设置dialog中不显示错误码信息
        //iat.setParameter("view_tips_plain","false");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        iat.setParameter(SpeechConstant.VAD_BOS, "10000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        iat.setParameter(SpeechConstant.VAD_EOS, "10000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        iat.setParameter(SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
       /* iat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        iat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");*/
    }

}

