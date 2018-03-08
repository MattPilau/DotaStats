package com.app.dotastats.dotastats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import comp.app.dotastats.utils.UtilsPreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();

        //new UtilsPreferences().clearFavoritePlayers(getApplicationContext());

        findViewById(R.id.findPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), PlayersActivity.class);

                String s = ((EditText) findViewById(R.id.namePlayerSearch)).getText().toString();

                if(s.equals(null) || s.equals(""))
                    Toast.makeText(getBaseContext(), "Please write a valid username !", Toast.LENGTH_SHORT).show();
                else {
                    myIntent.putExtra("namePlayer", s);
                    v.getContext().startActivity(myIntent);
                }
            }
        });

        findViewById(R.id.findHero).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), HeroesActivity.class);

                String s = ((EditText) findViewById(R.id.nameHeroSearch)).getText().toString();

                myIntent.putExtra("nameHero", s);
                v.getContext().startActivity(myIntent);
            }
        });

        findViewById(R.id.linkAllHeroes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), AllHeroes.class);
                v.getContext().startActivity(myIntent);
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
