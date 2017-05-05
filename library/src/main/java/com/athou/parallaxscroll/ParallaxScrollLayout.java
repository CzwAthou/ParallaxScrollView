package com.athou.parallaxscroll;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by athou on 2017/5/4.
 */

public class ParallaxScrollLayout extends LinearLayout {
    public ParallaxScrollLayout(Context context) {
        this(context, null);
    }

    public ParallaxScrollLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxScrollLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //拦截给子view的LayoutParams，替换为自定义的LayoutParams
        return new ParallaxScrollLayoutParams(getContext(), attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        ParallaxScrollLayoutParams parallaxParams = (ParallaxScrollLayoutParams) params;
        if (!parallaxParams.isDisscrollAble()) {
            super.addView(child, index, params);
        } else {
            //给每个子view外层再套一个自定义容器，我们操作这个容器滑动，从而达到使子view滑动的效果
            ParallaxChildContianer container = new ParallaxChildContianer(getContext());
            container.setScrollFromBgColor(parallaxParams.mParallaxscrollFromBgColor);
            container.setScrollToBgColor(parallaxParams.mParallaxscrollToBgColor);
            container.setScrollTranslation(parallaxParams.mParallaxscrollTranslation);
            container.setScrollAlpha(parallaxParams.mParallaxscrollAlpha);
            container.setScrollScaleX(parallaxParams.mParallaxscrollScaleX);
            container.setScrollScaleY(parallaxParams.mParallaxscrollScaleY);
            container.addView(child);
            super.addView(container, index, params);
        }
    }

    /**
     * 解析子控件中包含有自定义属性
     */
    private final class ParallaxScrollLayoutParams extends LinearLayout.LayoutParams {
        public int mParallaxscrollFromBgColor;
        public int mParallaxscrollToBgColor;
        public int mParallaxscrollTranslation;
        public boolean mParallaxscrollAlpha;
        public boolean mParallaxscrollScaleX;
        public boolean mParallaxscrollScaleY;

        public ParallaxScrollLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.ParallaxSV_LP);
            mParallaxscrollFromBgColor = typedArray.getColor(R.styleable.ParallaxSV_LP_parallax_fromBgColor, -1);
            mParallaxscrollToBgColor = typedArray.getColor(R.styleable.ParallaxSV_LP_parallax_toBgColor, -1);
            mParallaxscrollTranslation = typedArray.getInt(R.styleable.ParallaxSV_LP_parallax_translation, -1);
            mParallaxscrollAlpha = typedArray.getBoolean(R.styleable.ParallaxSV_LP_parallax_alpha, false);
            mParallaxscrollScaleX = typedArray.getBoolean(R.styleable.ParallaxSV_LP_parallax_scaleX, false);
            mParallaxscrollScaleY = typedArray.getBoolean(R.styleable.ParallaxSV_LP_parallax_scaleY, false);
            typedArray.recycle();
        }

        /**
         * 是否支持自定义滑动，如果子控件有任何一个自定义属性，即表示该控件为可滑动
         * @return
         */
        public boolean isDisscrollAble() {
            return mParallaxscrollAlpha || mParallaxscrollScaleX || mParallaxscrollScaleY ||
                    (mParallaxscrollFromBgColor != -1) || (mParallaxscrollToBgColor != -1) ||
                    (mParallaxscrollTranslation != -1);
        }
    }
}
