package com.example.film;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class FilmPref extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference minRating = findPreference(getString(R.string.settings_min_rating_key));
            bindPreferenceSummaryToValue(minRating);

            Preference itemNumber = findPreference(getString(R.string.settings_item_number_key));
            bindPreferenceSummaryToValue(itemNumber);

            Preference orderBy = findPreference(getString(R.string.settings_sort_by_key));
            bindPreferenceSummaryToValue(orderBy);
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else if (preference instanceof EditTextPreference) {
                if (preference.getKey() == getString(R.string.settings_item_number_key)) {
                    if (Integer.parseInt(stringValue) > 0 && Integer.parseInt(stringValue) <= 50)
                        preference.setSummary(stringValue);
                    else if (Integer.parseInt((stringValue)) <= 0)
                        preference.setSummary("1");
                    else
                        preference.setSummary("50");
                } else
                    preference.setSummary(stringValue);
            }
            return true;
        }
    }
}