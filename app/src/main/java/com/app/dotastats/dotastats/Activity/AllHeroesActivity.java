package com.app.dotastats.dotastats.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.app.dotastats.dotastats.Adapters.AllHeroAdapter;
import com.app.dotastats.dotastats.AllHeroesService;
import com.app.dotastats.dotastats.Beans.Hero;
import com.app.dotastats.dotastats.Beans.Heroes;
import com.app.dotastats.dotastats.Interfaces.AllHeroesInterface;
import com.app.dotastats.dotastats.R;

import java.util.ArrayList;

// display a list of all heroes in a recyclerview
public class AllHeroesActivity extends AppCompatActivity {

    private AllHeroAdapter adapter;
    private Heroes heroes;
    private Boolean request;

    private ServiceConnection maConnexion = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder service) {
            AllHeroesInterface myBinder = (AllHeroesInterface) service;
            myBinder.setBar((ProgressBar) findViewById(R.id.progressBarAllHeroes));
            myBinder.setHeroes(heroes);
            myBinder.setAdapter(adapter);
            myBinder.setButton((Button) findViewById(R.id.refresh));
        }

        public void onServiceDisconnected(ComponentName name) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_heroes);

        heroes = new Heroes();
        request = true;

        // RecyclerView adapter needs to be created at this point, and will be updated once the data are received from the API
        adapter = new AllHeroAdapter(new ArrayList<Hero>());
        ((RecyclerView) findViewById(R.id.AllHeroesList)).setAdapter(adapter);
        ((RecyclerView) findViewById(R.id.AllHeroesList)).setLayoutManager(new LinearLayoutManager(getBaseContext()));
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
    protected  void onStart(){
        super.onStart();

        // prevents the search service from starting multiple times
        if(request){
            final Intent intentAllHeroes = new Intent (getBaseContext(), AllHeroesService.class);
            bindService(intentAllHeroes, maConnexion, Context.BIND_AUTO_CREATE);
            startService(intentAllHeroes);
        }

        // if the user wants to add filters to his search
        findViewById(R.id.settingsSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), SettingsActivity.class);
                v.getContext().startActivity(myIntent);
            }
        });

        // once the results are displayed, the user can refresh (usually he will do it if he changed the filters)
        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(),AllHeroesService.class));
                heroes.getHeroes().clear();
                final Intent intentAllHeroes = new Intent (getBaseContext(), AllHeroesService.class);
                bindService(intentAllHeroes, maConnexion, Context.BIND_AUTO_CREATE);
                startService(intentAllHeroes);
            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        request = false;
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(maConnexion);
        stopService(new Intent(this,AllHeroesService.class));
    }
}
