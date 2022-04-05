package com.baidu.duer.chinatalk_refactor.animation;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @author wzc
 * @from QMUI
 * @date 202-3-12
 */
public class CardTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        // 刷新数据notifyDataSetChange之后也会调用到transformPage，但此时的position可能不在[-1, 1]之间
        if (position <= -1 || position >= 1f) {
            page.setRotation(0);
        } else {
            page.setRotation(position * 30);
            page.setPivotX(page.getWidth() * .5f);
            page.setPivotY(page.getHeight() * 1f);
        }
    }
}
