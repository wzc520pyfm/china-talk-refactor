package com.baidu.duer.chinatalk_refactor.ui.game;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.baidu.duer.chinatalk_refactor.iflytek.RecognizeListener;
import com.baidu.duer.chinatalk_refactor.iflytek.RecognizeSpeechManager;
import com.baidu.duer.chinatalk_refactor.ui.home.HomeViewModel;
import com.iflytek.cloud.SpeechError;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;

import com.baidu.duer.chinatalk_refactor.R;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

public class GameFragment extends FrameLayout {
    private View root;
    private Unbinder unbinder;

    @BindView(R.id.voice_input)
    LinearLayout voiceInput;
    @BindView(R.id.gameAnswerET)
    EditText gameAnswerET;
    @BindView(R.id.speak_image_btn)
    ImageButton speakImageBtn;

    Context mContext;

    public GameFragment(Context context) {
        super(context);
        this.mContext = context;

        root = LayoutInflater.from(context).inflate(R.layout.fragment_game,null);
        int width = QMUIDisplayHelper.dp2px(context, 400);
        int height = QMUIDisplayHelper.dp2px(context, 800);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
        lp.gravity = Gravity.CENTER;
        unbinder = ButterKnife.bind(this,root);

        addView(root, lp);

    }

    @OnClick(R.id.voice_btn)
    public void onClick1() {
        voiceInput.setVisibility(LinearLayout.VISIBLE);
    }
    @OnClick(R.id.close_input)
    public void onClick2() {
        voiceInput.setVisibility(LinearLayout.INVISIBLE);
    }
    @OnLongClick(R.id.speak_image_btn)
    public boolean onLongClick1() {
        Toast.makeText(mContext, "??????", Toast.LENGTH_SHORT).show();
        speakImageBtn.setImageResource(R.drawable.btn_voice_ongoing);
        RecognizeSpeechManager.instance().startRecognize();
        return true;
    }
    @OnTouch(R.id.speak_image_btn)
    public boolean onTouch1(View v, MotionEvent event) {
        if(v.getId()==R.id.speak_image_btn){
            if (event.getAction() == MotionEvent.ACTION_UP){
                Toast.makeText(mContext, "??????", Toast.LENGTH_SHORT).show();
                speakImageBtn.setImageResource(R.drawable.btn_voice_end);
                RecognizeSpeechManager.instance().stopRecognize();
            }
        }
        return false;
    }

    public void setImageResource(int imageResource) {
        QMUIRadiusImageView2 mRadiusImageView = root.findViewById(R.id.radiusImageView); // ????????????
        mRadiusImageView.setImageResource(imageResource); // ???????????????
//        mRadiusImageView.setBorderColor(
//                ContextCompat.getColor(getContext(), R.color.radiusImageView_border_color)); // ????????????
//        mRadiusImageView.setBorderWidth(QMUIDisplayHelper.dp2px(getContext(), 2)); // ????????????
        mRadiusImageView.setCornerRadius(QMUIDisplayHelper.dp2px(getContext(), 10)); // ????????????
//        mRadiusImageView.setSelectedMaskColor(
//                ContextCompat.getColor(getContext(), R.color.radiusImageView_selected_mask_color)); // ????????????????????????
//        mRadiusImageView.setSelectedBorderColor(
//                ContextCompat.getColor(getContext(), R.color.radiusImageView_selected_border_color)); // ????????????????????????
//        mRadiusImageView.setSelectedBorderWidth(QMUIDisplayHelper.dp2px(getContext(), 3)); // ??????????????????????????????
        mRadiusImageView.setTouchSelectModeEnabled(false); // ????????????????????????
        mRadiusImageView.setCircle(false);
    }

//    public void setText(CharSequence text) {
//        gameAnswerET.setText(text);
//    }
}
