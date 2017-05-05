package com.athou.parallaxscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by athou on 2017/5/4.
 */
public class ParallaxScrollView extends ScrollView {

    ParallaxScrollLayout mContent;

    public ParallaxScrollView(Context context) {
        super(context);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取sv的contentview
        mContent = (ParallaxScrollLayout) getChildAt(0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int screenHeight = getHeight();
        for (int i = 0; i < mContent.getChildCount(); i++) {
            View child = mContent.getChildAt(i);
            //如果contentview的子view不是自定义的可滑动的view,则跳过继续判断下一个子view
            if (!(child instanceof ParallaxScrollListener)) {
                continue;
            }
            ParallaxScrollListener listener = (ParallaxScrollListener) child;
            //获取子view相对sv的top
            int childTop = child.getTop();
            //获取子view的高度
            int childHeight = child.getHeight();
            //获取子view距离屏幕顶部的高度
            int absTop = childTop - t;
            if (absTop < screenHeight) { //child正处于窗口中，需要执行动画
                int visibleGap = screenHeight - absTop;
                float ratio = visibleGap / (float) childHeight ;
                listener.onParallaxScroll(clamp(ratio, 0f, 1f));
            } else { //child不正窗口中，重置至初始状态
                listener.onResetDisscroll();
            }
        }
    }

    private final float clamp(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }
}
