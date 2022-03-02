package com.baidu.duer.chinatalk_refactor.iflytek;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.baidu.duer.chinatalk_refactor.iflytek.ise.FinalResult;
import com.baidu.duer.chinatalk_refactor.iflytek.ise.Result;
import com.baidu.duer.chinatalk_refactor.iflytek.ise.xml.XmlResultParser;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;

import java.lang.ref.WeakReference;

/**
 * 语音评测
 */
public class EvaluateSpeechManager implements EvaluatorListener {

    private static final String TAG = "EvaluatSpeechManager";

    private final static String PREFER_NAME = "ise_settings";
    private final static int REQUEST_CODE_SETTINGS = 1;

    // 上下文的弱引用,以便在不使用时回收,避免内存泄露
    private WeakReference<Context> bindContext;
    // 结果回调对象
    private EvaluateListener evaluateListener;
    // 语音评测对象
    private SpeechEvaluator ise;
    // 解析结果
    private String lastResult;

    // 单例
    private static EvaluateSpeechManager instance;

    private EvaluateSpeechManager() {
    }

    /**
     * 单例方法
     */
    public static EvaluateSpeechManager instance() {
        if (instance == null) {
            instance = new EvaluateSpeechManager();
        }
        return instance;
    }

    /**
     * 设置结果回调对象
     */
    public void setEvaluateListener(EvaluateListener evaluateListener) {
        this.evaluateListener = evaluateListener;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        if (bindContext == null) {
            bindContext = new WeakReference<Context>(context);
        }
        if (ise == null) {
            ise = SpeechEvaluator.createEvaluator(bindContext.get(), null);
        }
    }

    /**
     * 开始评测
     * @String category 评测类型
     *  - read_syllable : 字
     *  - read_word : 词
     *  - read_sentence : 句
     *  - read_chapter : 诗
     * @String evaText 评测内容
     */
    public int startEvaluate(String category, String evaText) {
        lastResult = null;
        assert ise!=null;
        setParams(category);
        return ise.startEvaluating(evaText, null, this);
    }

    /**
     * 停止评测
     */
    public void stopEvaluate() {
        ise.stopEvaluating();
    }

    /**
     * 取消评测
     */
    public void cancelEvaluate() {
        ise.cancel();
        lastResult = null;
    }

    /**
     * 结果解析
     */
    public Result parseResult() {
        if(lastResult == null) {
            return new FinalResult();
        }
        XmlResultParser resultParser = new XmlResultParser();
        return resultParser.parse(lastResult);
    }

    public void release() {
        ise.cancel();
        ise.destroy();
        // ise = null;
        bindContext.clear();
        // bindContext = null;
    }

    @Override
    public void onVolumeChanged(int volume, byte[] data) {
        Log.d(TAG, "当前正在说话，音量大小 = " + volume + " 返回音频数据 = " + data.length);
    }

    @Override
    public void onBeginOfSpeech() {
        Log.d(TAG, "evaluator begin");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech isListening " + ise.isEvaluating());
    }

    @Override
    public void onResult(EvaluatorResult evaluatorResult, boolean isLast) {
        Log.d(TAG, "evaluator result :" + isLast);
        StringBuilder builder = new StringBuilder();
        builder.append(evaluatorResult.getResultString()); // evaluatorResult为原始的xml分析结果,需要调用解析函数来得到最终结果
        lastResult = builder.toString();
        if(evaluateListener != null) {
            evaluateListener.onNewResult(builder.toString());
            evaluateListener.onTotalResult(builder.toString(), isLast);
        }
    }

    @Override
    public void onError(SpeechError speechError) {
        if(evaluateListener != null) {
            evaluateListener.onError(speechError);
        }
    }

    @Override
    public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        Log.d(TAG, "onEvent type " + eventType);
    }

    private void setParams(String category) {
        // 设置评测语种
        String language = "zh_cn";
        // 设置结果等级（中文仅支持complete)
        String result_level = "complete";
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        String vad_bos = "5000";
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        String vad_eos = "1800";
        // 语音输入超时时间，即用户最多可以连续说多长时间；
        String speech_timeout = "-1";
        // 设置流式版本所需参数 : ent sub plev
        ise.setParameter("ent", "cn_vip");
        ise.setParameter(SpeechConstant.SUBJECT, "ise");
        ise.setParameter("plev", "0");

        // 设置评分百分制 使用 ise_unite  rst  extra_ability 参数
        ise.setParameter("ise_unite", "1");
        ise.setParameter("rst", "entirety");
        ise.setParameter("extra_ability", "syll_phone_err_msg;pitch;multi_dimension");

        ise.setParameter(SpeechConstant.LANGUAGE, language);
        // 设置需要评测的类型
        ise.setParameter(SpeechConstant.ISE_CATEGORY, category);
        ise.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        ise.setParameter(SpeechConstant.VAD_BOS, vad_bos);
        ise.setParameter(SpeechConstant.VAD_EOS, vad_eos);
        ise.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, speech_timeout);
        ise.setParameter(SpeechConstant.RESULT_LEVEL, result_level);
        ise.setParameter(SpeechConstant.AUDIO_FORMAT_AUE, "opus");
        // 设置音频保存路径，保存音频格式支持pcm、wav，
        /* ise.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        ise.setParameter(SpeechConstant.ISE_AUDIO_PATH,
                getExternalFilesDir("msc").getAbsolutePath() + "/ise.wav"); */
        //通过writeaudio方式直接写入音频时才需要此设置
        //mIse.setParameter(SpeechConstant.AUDIO_SOURCE,"-1");
    }
}
