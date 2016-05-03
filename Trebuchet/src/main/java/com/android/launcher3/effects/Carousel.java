package com.android.launcher3.effects;

import android.view.View;

import com.android.launcher3.PagedView;

/**
 * Created by lcg on 16-5-3.
 */
public class Carousel extends BaseEffectAnimation {

    public Carousel(PagedView pagedView) {
        super(Effect.CAROUSEL, pagedView);
    }

    @Override
    public void screenScrolled(View v, float progress) {
        float rotation = 90.0f * progress;

        v.setCameraDistance(mPagedView.mDensity * CAMERA_DISTANCE);
        v.setTranslationX(v.getMeasuredWidth() * progress);
        v.setPivotX(!mPagedView.isLayoutRtl() ? 0f : v.getMeasuredWidth());
        v.setPivotY(v.getMeasuredHeight() / 2);
        v.setRotationY(-rotation);
    }
}
