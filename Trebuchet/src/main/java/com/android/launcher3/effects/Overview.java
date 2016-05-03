package com.android.launcher3.effects;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.android.launcher3.PagedView;

/**
 * Created by lcg on 16-5-3.
 */
public class Overview extends BaseEffectAnimation {

    private AccelerateDecelerateInterpolator mScaleInterpolator = new AccelerateDecelerateInterpolator();

    public Overview(PagedView pagedView) {
        super(Effect.OVERVIEW, pagedView);
    }

    @Override
    public void screenScrolled(View v, float progress) {
        float scale =1.0f - 0.1f * mScaleInterpolator
                .getInterpolation(Math.min(0.3f, Math.abs(progress)) / 0.3f);

        v.setPivotX(progress < 0 ? 0 : v.getMeasuredWidth());
        v.setPivotY(v.getMeasuredHeight() * 0.5f);
        v.setScaleX(scale);
        v.setScaleY(scale);
        v.setAlpha(scale);
    }
}
