package com.app.dotastats.dotastats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

public class PlayerProfileActivity extends AppCompatActivity {
    Player player;

    private ServiceConnection maConnexion = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerProfileInterface myBinder = (PlayerProfileInterface) service;
            myBinder.setPlayer(player);
        }
        public void onServiceDisconnected(ComponentName name) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);
    }

    @Override
    protected void onStart(){
        super.onStart();
        player = new Player();

        Intent intent = getIntent();
        player.setId(intent.getStringExtra("idPlayer"));
        Log.i("start",player.getId());

        final Intent intentProfilePlayer = new Intent(getBaseContext(),PlayerProfileService.class);
        bindService(intentProfilePlayer, maConnexion, Context.BIND_AUTO_CREATE);
        startService(intentProfilePlayer);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(maConnexion);
        stopService(new Intent(this,PlayerProfileService.class));
    }
}
