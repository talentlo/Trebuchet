package com.android.launcher3.effects;

import android.view.View;

import com.android.launcher3.PagedView;
import com.android.launcher3.util.LogUtil;

/**
 * Created by lcg on 16-5-3.
 */
public class Zoom extends BaseEffectAnimation {
    private boolean zoomIn;

    public Zoom(boolean zoomIn, PagedView mPagedView) {
        super(zoomIn ? Effect.ZOOM_IN : Effect.ZOOM_OUT, mPagedView);
        this.zoomIn = zoomIn;
    }

    @Override
    public void screenScrolled(View v, float progress) {
        LogUtil.d("progress ="+progress);
        float scale = 1.0f + (zoomIn ? -0.2f : 0.2f) * Math.abs(progress);

        // Extra translation to account for the increase in size
        if (!zoomIn) {
            float translationX = v.getMeasuredWidth() * 0.1f * -progress;
            v.setTranslationX(translationX);
        }

        v.setScaleX(scale);
        v.setScaleY(scale);
    }
}
