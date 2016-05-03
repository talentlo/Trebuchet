package com.android.launcher3.effects;

import android.view.View;

import com.android.launcher3.PagedView;

/**
 * Created by lcg on 16-5-3.
 */
public class Flip extends BaseEffectAnimation {

    public Flip(PagedView pagedView) {
        super(Effect.FLIP, pagedView);
    }

    @Override
    public void screenScrolled(View v, float progress) {
        float rotation = -180.0f * Math.max(-1f, Math.min(1f, progress));

        v.setCameraDistance(mPagedView.mDensity * CAMERA_DISTANCE);
        v.setPivotX(v.getMeasuredWidth() * 0.5f);
        v.setPivotY(v.getMeasuredHeight() * 0.5f);
        v.setRotationY(rotation);

        if (progress >= -0.5f && progress <= 0.5f) {
            v.setTranslationX(v.getMeasuredWidth() * progress);
            if (v.getVisibility() != View.VISIBLE) {
                v.setVisibility(View.VISIBLE);
            }
        } else {
            v.setTranslationX(v.getMeasuredWidth() * -10f);
        }
    }
}
