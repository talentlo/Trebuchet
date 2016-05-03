package com.android.launcher3.effects;

import android.animation.TimeInterpolator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.android.launcher3.CellLayout;
import com.android.launcher3.PagedView;

/**
 * Created by lcg on 16-5-3.
 */
public class Stack extends BaseEffectAnimation {

    protected AccelerateInterpolator mAlphaInterpolator = new AccelerateInterpolator(0.9f);
    private ZInterpolator mZInterpolator = new ZInterpolator(0.5f);
    private DecelerateInterpolator mLeftScreenAlphaInterpolator = new DecelerateInterpolator(4);

    public Stack(PagedView pagedView) {
        super(Effect.STACK, pagedView);
    }

    @Override
    public void screenScrolled(View v, float progress) {
        final boolean isRtl = mPagedView.isLayoutRtl();
        float interpolatedProgress;
        float translationX;
        float maxScrollProgress = Math.max(0, progress);
        float minScrollProgress = Math.min(0, progress);

        if (mPagedView.isLayoutRtl()) {
            translationX = maxScrollProgress * v.getMeasuredWidth();
            interpolatedProgress = mZInterpolator.getInterpolation(Math.abs(maxScrollProgress));
        } else {
            translationX = minScrollProgress * v.getMeasuredWidth();
            interpolatedProgress = mZInterpolator.getInterpolation(Math.abs(minScrollProgress));
        }
        float scale = (1 - interpolatedProgress) + interpolatedProgress * TRANSITION_SCALE_FACTOR;

        float alpha;
        if (isRtl && (progress > 0)) {
            alpha = mAlphaInterpolator.getInterpolation(1 - Math.abs(maxScrollProgress));
        } else if (!isRtl && (progress < 0)) {
            alpha = mAlphaInterpolator.getInterpolation(1 - Math.abs(progress));
        } else {
            // On large screens we need to fade the page as it nears its leftmost position
            alpha = mLeftScreenAlphaInterpolator.getInterpolation(1 - progress);
        }

        v.setTranslationX(translationX);
        v.setScaleX(scale);
        v.setScaleY(scale);
        if (v instanceof CellLayout) {
            ((CellLayout) v).getShortcutsAndWidgets().setAlpha(alpha);
        } else {
            v.setAlpha(alpha);
        }

        // If the view has 0 alpha, we move it off screen so as to prevent
        // it from accepting touches
        if (alpha == 0) {
            v.setTranslationX(v.getMeasuredWidth() * -10f);
        } else if (v.getVisibility() != View.VISIBLE) {
            v.setVisibility(View.VISIBLE);
        }
    }

    /*
    * This interpolator emulates the rate at which the perceived scale of an object changes as its
    * distance from a camera increases. When this interpolator is applied to a scale animation on a
    * view, it evokes the sense that the object is shrinking due to moving away from the camera.
     */
    static class ZInterpolator implements TimeInterpolator {
        private float focalLength;

        public ZInterpolator(float foc) {
            focalLength = foc;
        }

        public float getInterpolation(float input) {
            return (1.0f - focalLength / (focalLength + input))
                    / (1.0f - focalLength / (focalLength + 1.0f));
        }

        /*
         * The exact reverse of ZInterpolator.
         */
        static class InverseZInterpolator implements TimeInterpolator {
            private ZInterpolator zInterpolator;

            public InverseZInterpolator(float foc) {
                zInterpolator = new ZInterpolator(foc);
            }

            public float getInterpolation(float input) {
                return 1 - zInterpolator.getInterpolation(1 - input);
            }
        }
    }


}
