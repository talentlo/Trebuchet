package com.android.launcher3.effects;

import android.view.View;

import com.android.launcher3.PagedView;
import com.android.launcher3.R;
import com.android.launcher3.util.LogUtil;

/**
 * Created by lcg on 16-5-3.
 */
public abstract class BaseEffectAnimation {

    protected static final float TRANSITION_SCREEN_ROTATION = 12.5f;
    protected static final float TRANSITION_SCALE_FACTOR = 0.74f;
    protected static float CAMERA_DISTANCE = 6500;
    protected final PagedView mPagedView;
    private final Effect mEffect;

    public BaseEffectAnimation(Effect mEffect, PagedView mPagedView) {
        this.mEffect = mEffect;
        this.mPagedView = mPagedView;
    }

    public static void setEffectAnimation(PagedView mPagedView, Effect mEffect) {
        int effectType = mEffect.getEffectType();
        BaseEffectAnimation animation = getEffectAnimationForType(mPagedView, effectType);
        mPagedView.setEffectAnimation(animation);
    }

    public abstract void screenScrolled(View v, float progress);

    public static BaseEffectAnimation getEffectAnimationForType(PagedView mPagedView, int effectType) {
        switch (effectType) {
            case 0:
                return null;
            case 1:
                return new Zoom(true, mPagedView);
            case 2:
                return new Zoom(false, mPagedView);
            case 3:
                return new Rotate(true, mPagedView);
            case 4:
                return new Rotate(false, mPagedView);
            case 5:
                return new Cube(true, mPagedView);
            case 6:
                return new Cube(false, mPagedView);
            case 7:
                return new Stack(mPagedView);
            case 8:
                return new Accordion(mPagedView);
            case 9:
                return new Flip(mPagedView);
            case 10:
                return new Cylinder(true, mPagedView);
            case 11:
                return new Cylinder(false, mPagedView);
            case 12:
                return new Carousel(mPagedView);
            case 13:
                return new Overview(mPagedView);
            case 14:
                return new Cross(mPagedView);
            default:
                return null;
        }
    }

    public enum Effect {

        NO_EFFECT(0),
        ZOOM_IN(1),
        ZOOM_OUT(2),
        ROTATE_UP(3),
        ROTATE_DOWN(4),
        CUBE_IN(5),
        CUBE_OUT(6),
        STACK(7),
        ACCORDION(8),
        FLIP(9),
        CYLINDER_IN(10),
        CYLINDER_OUT(11),
        CAROUSEL(12),
        OVERVIEW(13),
        CROSS(14);

        private int effectType;

        Effect(int effectType) {
            this.effectType = effectType;
        }

        public static Effect getEffectForType(int effectType) {
            switch (effectType) {
                case 0:
                    return NO_EFFECT;
                case 1:
                    return ZOOM_IN;
                case 2:
                    return ZOOM_OUT;
                case 3:
                    return ROTATE_UP;
                case 4:
                    return ROTATE_DOWN;
                case 5:
                    return CUBE_IN;
                case 6:
                    return CUBE_OUT;
                case 7:
                    return STACK;
                case 8:
                    return ACCORDION;
                case 9:
                    return FLIP;
                case 10:
                    return CYLINDER_IN;
                case 11:
                    return CYLINDER_OUT;
                case 12:
                    return CAROUSEL;
                case 13:
                    return OVERVIEW;
                case 14:
                    return CROSS;
                default:
                    return NO_EFFECT;
            }
        }

        public static int getEffectPreviewResId(int effectType) {
            if (effectType<0 && effectType >= NAMES_RES_ID.length) {
                effectType = 0;
                LogUtil.w("The effectType is Illegal !");
            }
            return PREVIEW_RES_ID[effectType];
        }

        public static int getEffectStringResId(int effectType) {
            if (effectType<0 && effectType >= NAMES_RES_ID.length) {
                effectType = 0;
                LogUtil.w("The effectType is Illegal !");
            }
            return NAMES_RES_ID[effectType];
        }

        public int getEffectType() {
            return effectType;
        }

        @Override
        public String toString() {
            return String.valueOf(this.effectType);
        }

        public static final int[] NAMES_RES_ID = {
                R.string.effect_none,
                R.string.effect_zoom_in,
                R.string.effect_zoom_out,
                R.string.effect_rotate_up,
                R.string.effect_rotate_down,
                R.string.effect_cube_in,
                R.string.effect_cube_out,
                R.string.effect_stack,
                R.string.effect_accordion,
                R.string.effect_flip,
                R.string.effect_cylinder_in,
                R.string.effect_cylinder_out,
                R.string.effect_carousel,
                R.string.effect_overview,
                R.string.effect_cross
        };

        public static final int[] PREVIEW_RES_ID = {
                R.drawable.effect_none,
                R.drawable.effect_zoom_in,
                R.drawable.effect_zoom_out,
                R.drawable.effect_rotate_up,
                R.drawable.effect_rotate_down,
                R.drawable.effect_cube_in,
                R.drawable.effect_cube_out,
                R.drawable.effect_stack,
                R.drawable.effect_accordion,
                R.drawable.effect_flip,
                R.drawable.effect_cylinder_in,
                R.drawable.effect_cylinder_out,
                R.drawable.effect_carousel,
                R.drawable.effect_overview,
                R.drawable.effect_cross
        };
    }
}
