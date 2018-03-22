package com.app.dotastats.dotastats;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Matt on 22/03/2018.
 */

public class PreferenceScreenFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_fragment);
    }
}