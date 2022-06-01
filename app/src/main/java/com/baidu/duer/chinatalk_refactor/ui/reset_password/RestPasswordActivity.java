package com.baidu.duer.chinatalk_refactor.ui.reset_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.baidu.duer.chinatalk_refactor.R;
import com.baidu.duer.chinatalk_refactor.base.BaseActivity;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route("resetPwd")
public class RestPasswordActivity extends BaseActivity implements View.OnTouchListener {

    private String TAG = "LoginActivity";
    @BindView(R.id.container)
    ConstraintLayout layout;
    private GestureDetector mGestureDetector;
    private Context mContext;

    private static final int FLING_MIN_DISTANCE = 50;
    private static final int FLING_MIN_VELOCITY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_password);
        ButterKnife.bind(this);
        mContext = this;

        mGestureDetector = new GestureDetector(this, myGestureListener);
        layout.setOnTouchListener(this);
        layout.setLongClickable(true);//必需设置这为true 否则也监听不到手势
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * 滑动监听
     */
    GestureDetector.SimpleOnGestureListener myGestureListener = new GestureDetector.SimpleOnGestureListener(){
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            Log.e(TAG, "开始滑动");
            float x = e1.getX()-e2.getX();
            float x2 = e2.getX()-e1.getX();
            if(x > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
                Log.i(TAG,"向左手势");
            }else if(x2 > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
                Log.i(TAG,"向右手势");
                Router.build("login").anim(R.anim.left_in, R.anim.right_out).go(mContext);
            }
            return false;
        };
    };
}