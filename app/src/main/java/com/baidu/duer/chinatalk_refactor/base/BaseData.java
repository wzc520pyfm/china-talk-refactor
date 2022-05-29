package com.baidu.duer.chinatalk_refactor.base;

import com.baidu.duer.chinatalk_refactor.R;


public class BaseData {
    /**
     * NavBar激活状态下的lottie动画
     */
    static private int[] NavItemsActiveLottieAnimation = { R.raw.lottie_home, R.raw.litte_exam, R.raw.lottie_my };
    /**
     * NavBar沉默状态下的lottie动画
     */
    static private int[] NavItemsSilenceLottieAnimation = { R.raw.litte_home_silence, R.raw.litte_exam_silence, R.raw.litte_my_silence };

    static public int[] getNavItemsActiveLottieAnimation() {
        return NavItemsActiveLottieAnimation;
    }

    static public int[] getNavItemsSilenceLottieAnimation() {
        return NavItemsSilenceLottieAnimation;
    }
}
