package com.athou.parallaxscroll;

/**
 * Created by athou on 2017/5/4.
 */

public interface ParallaxScrollListener {
    /**
     * 开始滑动
     *
     * @param ratio 滑动百分比
     */
    void onParallaxScroll(float ratio);

    /**
     * 滑动结束，重置view到初始状态
     */
    void onResetDisscroll();
}
