package com.android.launcher3.effects;

import android.view.View;

import com.android.launcher3.PagedView;

/**
 * Created by lcg on 16-5-3.
 */
public class Accordion extends BaseEffectAnimation {

    public Accordion(PagedView pagedView) {
        super(Effect.ACCORDION, pagedView);
    }

    @Override
    public void screenScrolled(View v, float progress) {
        float scale = 1.0f - Math.abs(progress);
        v.setScaleX(scale);
        v.setPivotX(progress < 0 ? 0 : v.getMeasuredWidth());
        v.setPivotY(v.getMeasuredHeight() / 2f);
    }
}
