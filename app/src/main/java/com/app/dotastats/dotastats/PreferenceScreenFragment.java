package com.app.dotastats.dotastats;

import android.os.Bundle;
import android.preference.PreferenceFragment;


public class PreferenceScreenFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_fragment);
    }
}