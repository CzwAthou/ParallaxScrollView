package com.athou.parallaxscroll;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by athou on 2017/5/4.
 */

final class ParallaxChildContianer extends FrameLayout implements ParallaxScrollListener {
    private static final int TRANSLATION_FROM_TOP = 0x01;
    private static final int TRANSLATION_FROM_BOTTOM = 0x02;
    private static final int TRANSLATION_FROM_LEFT = 0x04;
    private static final int TRANSLATION_FROM_RIGHT = 0x08;

    /**
     * 颜色估值器
     */
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private int mWidth = 0;
    private int mHeight = 0;

    private int mParallaxscrollFromBgColor;
    private int mParallaxscrollToBgColor;
    private int mParallaxscrollTranslation;
    private boolean mParallaxscrollAlpha;
    private boolean mParallaxscrollScaleX;
    private boolean mParallaxscrollScaleY;

    public ParallaxChildContianer(Context context) {
        super(context);
    }

    public ParallaxChildContianer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxChildContianer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //保存宽高
        mWidth = w;
        mHeight = h;
    }

    public void setScrollFromBgColor(int mParallaxscrollFromBgColor) {
        this.mParallaxscrollFromBgColor = mParallaxscrollFromBgColor;
    }

    public void setScrollToBgColor(int mParallaxscrollToBgColor) {
        this.mParallaxscrollToBgColor = mParallaxscrollToBgColor;
    }

    public void setScrollTranslation(int mParallaxscrollTranslation) {
        this.mParallaxscrollTranslation = mParallaxscrollTranslation;
    }

    public void setScrollAlpha(boolean mParallaxscrollAlpha) {
        this.mParallaxscrollAlpha = mParallaxscrollAlpha;
    }

    public void setScrollScaleX(boolean mParallaxscrollScaleX) {
        this.mParallaxscrollScaleX = mParallaxscrollScaleX;
    }

    public void setScrollScaleY(boolean mParallaxscrollScaleY) {
        this.mParallaxscrollScaleY = mParallaxscrollScaleY;
    }

    @Override
    public void onParallaxScroll(float ratio) {
        if (mParallaxscrollAlpha) {
            setAlpha(ratio);
        }
        if (mParallaxscrollScaleX) {
            setScaleX(ratio);
        }
        if (mParallaxscrollScaleY) {
            setScaleY(ratio);
        }
        if (isTranslationfrom(TRANSLATION_FROM_LEFT)) { // -mWidth --> 0
            setTranslationX(-mWidth * (1 - ratio));
        }
        if (isTranslationfrom(TRANSLATION_FROM_TOP)) {// -mHeight --> 0
            setTranslationY(-mHeight * (1 - ratio));
        }
        if (isTranslationfrom(TRANSLATION_FROM_RIGHT)) { // mWidth --> 0
            setTranslationX(mWidth * (1 - ratio));
        }
        if (isTranslationfrom(TRANSLATION_FROM_BOTTOM)) {
            setTranslationY(mHeight * (1 - ratio)); // mHeight --> 0
        }
        if (mParallaxscrollFromBgColor != -1 && mParallaxscrollToBgColor != -1) {
            setBackgroundColor((Integer) argbEvaluator.evaluate(ratio, mParallaxscrollFromBgColor, mParallaxscrollToBgColor));
        }
    }

    /**
     * 判断是否支持给定的位移动画
     *
     * @param translation
     * @return
     */
    private boolean isTranslationfrom(int translation) {
        if (mParallaxscrollTranslation == -1) {
            return false;
        }
        return (mParallaxscrollTranslation & translation) == translation;
    }

    @Override
    public void onResetDisscroll() {
        if (mParallaxscrollAlpha) {
            setAlpha(0);
        }
        if (mParallaxscrollScaleX) {
            setScaleX(0);
        }
        if (mParallaxscrollScaleY) {
            setScaleY(0);
        }
        if (isTranslationfrom(TRANSLATION_FROM_LEFT)) { // -mWidth --> 0
            setTranslationX(-mWidth);
        }
        if (isTranslationfrom(TRANSLATION_FROM_TOP)) {
            setTranslationY(-mHeight);
        }
        if (isTranslationfrom(TRANSLATION_FROM_RIGHT)) {
            setTranslationX(mWidth);
        }
        if (isTranslationfrom(TRANSLATION_FROM_BOTTOM)) {
            setTranslationY(mHeight); // mHeight --> 0
        }
        if (mParallaxscrollFromBgColor != -1 && mParallaxscrollToBgColor != -1) {
            setBackgroundColor(mParallaxscrollFromBgColor);
        }
    }
}
