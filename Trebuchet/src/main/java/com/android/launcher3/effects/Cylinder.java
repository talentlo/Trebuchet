package com.android.launcher3.effects;

import android.view.View;

import com.android.launcher3.PagedView;

/**
 * Created by lcg on 16-5-3.
 */
public class Cylinder extends BaseEffectAnimation {
    private boolean cylinderIn;

    public Cylinder(boolean cylinderIn, PagedView mPagedView) {
        super(cylinderIn ? Effect.CYLINDER_IN : Effect.CYLINDER_OUT, mPagedView);
        this.cylinderIn = cylinderIn;
    }

    @Override
    public void screenScrolled(View v, float progress) {
        float rotation =
                (cylinderIn ? TRANSITION_SCREEN_ROTATION : -TRANSITION_SCREEN_ROTATION) * progress;

        v.setPivotX((progress + 1) * v.getMeasuredWidth() * 0.5f);
        v.setPivotY(v.getMeasuredHeight() * 0.5f);
        v.setRotationY(rotation);
    }
}
