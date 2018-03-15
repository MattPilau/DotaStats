package com.app.dotastats.dotastats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
