/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intel.vpp;

import android.app.Activity;

import android.content.SharedPreferences;

import android.os.Bundle;

import android.util.Log;

import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

public class VppSettings extends Activity implements CompoundButton.OnCheckedChangeListener,
     AdapterView.OnItemSelectedListener {

    private static final String TAG = "VppSettings";

    private static final String VPP_SHARED_PREF = "vpp_settings";
    private static final String VPP_STATUS = "vpp_status";
    private static final String VPP_STRENGTH = "strength";
    private static final String[] VPP_STRENGTH_VALUES = {"0low", "1medium", "2high"};

    private boolean mStatus;
    private int mStrength;
    private SharedPreferences mSharedPref;
    private Switch mSwitch;
    private View box;
    private View divider;
    private Spinner spinner;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.act_settings);

        mSwitch = (Switch)findViewById(R.id.switcher);
        box = (View)findViewById(R.id.box);
        divider = (View)findViewById(R.id.divider);
        spinner = (Spinner)findViewById(R.id.spinner);

        mSharedPref = getSharedPreferences(VPP_SHARED_PREF, Activity.MODE_WORLD_READABLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        mStatus = mSharedPref.getBoolean(VPP_STATUS, false);
        mStrength = Integer.valueOf(mSharedPref.getString(VPP_STRENGTH, "1medium").substring(0, 1));
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(VPP_STRENGTH, VPP_STRENGTH_VALUES[mStrength]).commit();
        mSwitch.setOnCheckedChangeListener(this);
        mSwitch.setChecked(mStatus);
        checkUI();
        spinner.setSelection(mStrength);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSwitch.setOnCheckedChangeListener(null);
    }

    private void checkUI() {
        if (mStatus) {
            box.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        } else {
            box.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mStrength = position;
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(VPP_STRENGTH, VPP_STRENGTH_VALUES[mStrength]).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mStatus = isChecked;
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putBoolean(VPP_STATUS, mStatus).commit();
        checkUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
