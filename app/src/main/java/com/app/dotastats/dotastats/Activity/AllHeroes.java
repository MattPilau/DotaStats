package com.app.dotastats.dotastats.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.app.dotastats.dotastats.Interfaces.AllHeroesInterface;
import com.app.dotastats.dotastats.AllHeroesService;
import com.app.dotastats.dotastats.R;
import com.app.dotastats.dotastats.SearchPlayerService;

public class AllHeroes extends AppCompatActivity {


    private ServiceConnection maConnexion = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder service) {
            AllHeroesInterface myBinder = (AllHeroesInterface) service;
            myBinder.setListView((ListView) findViewById(R.id.AllHeroesList));
        }

        public void onServiceDisconnected(ComponentName name) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_heroes);

        final Intent intentAllHeroes = new Intent (getBaseContext(), AllHeroesService.class);
        bindService(intentAllHeroes, maConnexion, Context.BIND_AUTO_CREATE);
        startService(intentAllHeroes);
    }

    @Override
    protected  void onStart(){
        super.onStart();
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
        stopService(new Intent(this,SearchPlayerService.class));
    }








}
