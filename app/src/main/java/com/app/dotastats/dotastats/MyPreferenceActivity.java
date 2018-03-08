package com.app.dotastats.dotastats;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MyPreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_layout);

        // set the parameter "Username" to "obi-wan77"
        /*SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("namePlayer",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.Username), "obi_wan77");
        editor.commit();

        // get the data from the preferences ; Mamie is the default value if the key isn't allocated
        String name = sharedPref.getString(getString(R.string.Username),"Mamie_Chiffons");
        Toast.makeText(getBaseContext(), "Name : " + name, Toast.LENGTH_SHORT).show();*/
    }
}
