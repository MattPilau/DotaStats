package com.app.dotastats.dotastats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

// activity used when a user writes the name of a hero => the app displays to him each hero corresponding to what he wrote

public class HeroesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroes);
    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = getIntent();
        ((TextView)findViewById(R.id.nameHero)).setText(intent.getStringExtra("nameHero"));
    }
}
