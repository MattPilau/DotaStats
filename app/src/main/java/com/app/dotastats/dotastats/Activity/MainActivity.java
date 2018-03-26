package com.app.dotastats.dotastats.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.dotastats.dotastats.FavoritePlayerLastGameService;
import com.app.dotastats.dotastats.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Action bar => makes the whole application lagging ?!

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent homeIntent = new Intent(this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onStart(){
        super.onStart();

        //UtilsPreferences.clearFavoritePlayers(getApplicationContext());
        Intent intentService = new Intent(getApplicationContext(), FavoritePlayerLastGameService.class);
        startService(intentService);

        findViewById(R.id.findPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), PlayersActivity.class);

                String s = ((EditText) findViewById(R.id.namePlayerSearch)).getText().toString();

                if(s.equals(""))
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
                Intent myIntent = new Intent(v.getContext(), AllHeroesActivity.class);
                v.getContext().startActivity(myIntent);
            }
        });

        findViewById(R.id.linkAllFavoritePlayers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), FavoritePlayersActivity.class);
                v.getContext().startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        stopService(new Intent(this,FavoritePlayerLastGameService.class));
    }
}
