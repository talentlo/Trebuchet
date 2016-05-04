package com.android.launcher3.effects;

import android.view.View;

import com.android.launcher3.PagedView;

/**
 * Created by lcg on 16-5-3.
 */
public class Cube extends BaseEffectAnimation {

    private boolean cubIn;

    public Cube(boolean cubIn, PagedView mPagedView) {
        super(cubIn ? Effect.CUBE_IN : Effect.CUBE_OUT, mPagedView);
        this.cubIn = cubIn;
    }

    @Override
    public void screenScrolled(View v, float progress) {
        float rotation = (cubIn ? 90.0f : -90.0f) * progress;

        if (cubIn) {
            v.setCameraDistance(mPagedView.mDensity * CAMERA_DISTANCE);
        }

        v.setPivotX(progress < 0 ? 0 : v.getMeasuredWidth());
        v.setPivotY(v.getMeasuredHeight() * 0.5f);
        v.setRotationY(rotation);
    }
}
