/*
 * Copyright (C) 2016 Angad Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cookie.cordovaplugindemo.filepicker;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.cookie.cordovaplugindemo.R;
import com.cookie.filepicker.view.FilePickerPreference;


/**<p>
 * Created by Angad on 22/07/2016.
 * </p>
 */

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener
{   @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        FilePickerPreference fileDialog=(FilePickerPreference)findPreference("directories");
        fileDialog.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if(preference.getKey().equals("directories"))
        {   String value=(String)o;
            String arr[]=value.split(":");
            for(String path:arr)
                Toast.makeText(SettingsActivity.this,path, Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
