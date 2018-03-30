package com.app.dotastats.dotastats.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.dotastats.dotastats.FavoritePlayerLastGameService;
import com.app.dotastats.dotastats.R;
import com.app.dotastats.dotastats.utils.UtilsHttp;

public class MainActivity extends AppCompatActivity {

    Boolean startRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startRequest = true;
    }

    // Action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:{
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;
            }
            case R.id.heart:{
                Intent intent = new Intent(this, FavoritePlayersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        // prevents of starting a new service each time the user comes back to the main screen
        if(startRequest) {
            UtilsHttp.startRepetitiveRequest(getApplicationContext());
        }

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
    protected void onRestart() {
        super.onRestart();
        startRequest = true;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        stopService(new Intent(this,FavoritePlayerLastGameService.class));
    }
}
