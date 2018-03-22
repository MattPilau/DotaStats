package com.app.dotastats.dotastats.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.app.dotastats.dotastats.Adapters.AllHeroAdapter;
import com.app.dotastats.dotastats.Adapters.PlayerAdapter;
import com.app.dotastats.dotastats.Beans.Hero;
import com.app.dotastats.dotastats.Beans.Heroes;
import com.app.dotastats.dotastats.Beans.Player;
import com.app.dotastats.dotastats.Interfaces.AllHeroesInterface;
import com.app.dotastats.dotastats.AllHeroesService;
import com.app.dotastats.dotastats.R;
import com.app.dotastats.dotastats.SearchPlayerService;

import java.util.ArrayList;

public class AllHeroesActivity extends AppCompatActivity {

    private AllHeroAdapter adapter;
    private Heroes heroes;

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

        final Intent intentAllHeroes = new Intent (getBaseContext(), AllHeroesService.class);
        bindService(intentAllHeroes, maConnexion, Context.BIND_AUTO_CREATE);
        startService(intentAllHeroes);

        // RecyclerView adapter needs to be created at this point, and will be updated once the data are received from the API
        adapter = new AllHeroAdapter(new ArrayList<Hero>());
        ((RecyclerView) findViewById(R.id.AllHeroesList)).setAdapter(adapter);
        ((RecyclerView) findViewById(R.id.AllHeroesList)).setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    protected  void onStart(){
        super.onStart();

        findViewById(R.id.settingsSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), SettingsActivity.class);
                v.getContext().startActivity(myIntent);
            }
        });

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
