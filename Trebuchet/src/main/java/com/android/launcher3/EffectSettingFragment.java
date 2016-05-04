/*
 * Copyright (C) 2014 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.launcher3;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.launcher3.effects.BaseEffectAnimation;
import com.android.launcher3.settings.SettingsProvider;
import com.android.launcher3.util.LogUtil;

public class EffectSettingFragment extends Fragment {

    public static final String EFFECT_SETTING_FRAGMENT = "EffectSettingFragment";
    View mCurrentSelection;
    BaseEffectAnimation.Effect mCurrentEffect;
    private ListView mListView;
    private EffectListAdapter mAdapter;
    private ImageView mImageView;
    View.OnClickListener mSettingsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            LogUtil.d("position ="+position);
            mCurrentEffect = BaseEffectAnimation.Effect.getEffectForType(position);

            setCleared(mCurrentSelection);
            setSelected(v);
            mCurrentSelection = v;

//            ((EffectListAdapter) mListView.getAdapter()).notifyDataSetChanged();
//            mAdapter.notifyDataSetInvalidated();
            updateEffectPreview(mCurrentEffect);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.effect_setting_screen, container, false);
        LinearLayout titleLayout = (LinearLayout) v.findViewById(R.id.effect_setting_title);
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEffect();
            }
        });

        mImageView = (ImageView) v.findViewById(R.id.effect_image);


        mCurrentEffect = BaseEffectAnimation.Effect.getEffectForType(
                SettingsProvider.getIntCustomDefault(getActivity(),
                        SettingsProvider.SETTINGS_UI_WORKSPACE_EFFECT, 0));

        updateEffectPreview(mCurrentEffect);

        mListView = (ListView) v.findViewById(R.id.effect_list);

        mAdapter = new EffectListAdapter(getActivity(), BaseEffectAnimation.Effect.NAMES_RES_ID);
        mListView.setAdapter(mAdapter);
        return v;
    }

    public void setEffect() {
        ((Launcher) getActivity()).setLauncherEffect(mCurrentEffect);
    }

    private void updateEffectPreview(BaseEffectAnimation.Effect effect) {
        mImageView.setBackgroundResource(effect.getEffectPreviewResId(effect.getEffectType()));
    }

    private void setCleared(View v) {
        if(v == null){
            return;
        }
        v.setBackgroundColor(getResources().getColor(R.color.settings_bg_color));
        TextView t = (TextView) v.findViewById(R.id.item_name);
        t.setTextColor(Color.WHITE);
    }

    private void setSelected(View v) {
        if(v == null){
            return;
        }
        v.setBackgroundColor(Color.WHITE);
        TextView t = (TextView) v.findViewById(R.id.item_name);
        t.setTextColor(getResources().getColor(R.color.settings_bg_color));
    }

    private class EffectListAdapter extends BaseAdapter {
        Context mContext;
        int[] mTitleResIds;

        public EffectListAdapter(Context mContext, int[] mTitleResIds) {
            this.mContext = mContext;
            this.mTitleResIds = mTitleResIds;
        }

        @Override
        public int getCount() {
            return mTitleResIds.length;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return mContext.getString(mTitleResIds[position]);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)
                        mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.settings_pane_list_item, parent, false);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.item_name);

            // RTL
            Configuration config = getResources().getConfiguration();
            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                textView.setGravity(Gravity.RIGHT);
            }

            LogUtil.d("position ="+position+" mCurrentEffect ="+mCurrentEffect);
            // Set selected state
            if (position == mCurrentEffect.getEffectType()) {
                mCurrentSelection = convertView;
                setSelected(mCurrentSelection);
            }else{
                setCleared(convertView);
            }

            textView.setText(mTitleResIds[position]);

            convertView.setOnClickListener(mSettingsItemListener);
            convertView.setTag(position);
            return convertView;
        }

    }

}
