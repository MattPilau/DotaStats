package com.app.dotastats.dotastats;

import android.os.Bundle;
import android.preference.PreferenceFragment;

// used in the settings
// the user can add filters to be more precise when he searches a hero
public class PreferenceScreenFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_fragment);
    }
}