package com.udylity.socialization;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private static SharedPreferences getPrefs;

    private static String sociologistData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        setupActionBar();

        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        ListPreference sociologistPref = (ListPreference) findPreference(getString(R.string.sociologist_preference_key));
        sociologistPref.setOnPreferenceChangeListener(mPreferenceChangeListener);

        sociologistData = sociologistPref.getValue();
        sociologistPref.setSummary(getResources().getStringArray(R.array.sociologistNames)[Integer.parseInt(sociologistData)]);

    }

    private Preference.OnPreferenceChangeListener mPreferenceChangeListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            sociologistData = value.toString();
            preference.setSummary(getResources().getStringArray(R.array.sociologistNames)[Integer.parseInt(sociologistData)]);
            getPrefs.edit()
                    .putString(getString(R.string.sociologist_data_key), sociologistData)
                    .apply();
            return true;
        }
    };

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(featureId, item)) {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
