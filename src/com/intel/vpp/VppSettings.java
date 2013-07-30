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

import android.widget.CompoundButton;
import android.widget.Switch;

public class VppSettings extends Activity implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "VppSettings";

    private static final String VPP_SHARED_PREF = "vpp_settings";
    private static final String VPP_STATUS = "vpp_status";

    private boolean mStatus;
    private SharedPreferences mSharedPref;
    private Switch mSwitch;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.act_settings);

        mSwitch = (Switch)findViewById(R.id.switcher);
        mSharedPref = getSharedPreferences(VPP_SHARED_PREF, Activity.MODE_WORLD_READABLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        mStatus = mSharedPref.getBoolean(VPP_STATUS, false);
        SharedPreferences.Editor editor = mSharedPref.edit();
        mSwitch.setOnCheckedChangeListener(this);
        mSwitch.setChecked(mStatus);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSwitch.setOnCheckedChangeListener(null);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mStatus = isChecked;
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putBoolean(VPP_STATUS, mStatus).commit();
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
