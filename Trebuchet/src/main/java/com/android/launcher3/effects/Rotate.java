package com.android.launcher3.effects;

import android.view.View;

import com.android.launcher3.PagedView;

/**
 * Created by lcg on 16-5-3.
 */
public class Rotate extends BaseEffectAnimation {

    private boolean rotateUp;

    public Rotate(boolean rotateUp, PagedView mPagedView) {
        super(rotateUp ? Effect.ROTATE_UP : Effect.ROTATE_DOWN, mPagedView);
        this.rotateUp = rotateUp;
    }

    @Override
    public void screenScrolled(View v, float progress) {
        float rotation =
                (rotateUp ? TRANSITION_SCREEN_ROTATION : -TRANSITION_SCREEN_ROTATION) * progress;

        float translationX = v.getMeasuredWidth() * progress;

        float rotatePoint =
                (v.getMeasuredWidth() * 0.5f)
                        / (float) Math.tan(Math.toRadians((double) (TRANSITION_SCREEN_ROTATION * 0.5f)));

        v.setPivotX(v.getMeasuredWidth() * 0.5f);
        if (rotateUp) {
            v.setPivotY(-rotatePoint);
        } else {
            v.setPivotY(v.getMeasuredHeight() + rotatePoint);
        }
        v.setRotation(rotation);
        v.setTranslationX(translationX);
    }
}
