package com.app.dotastats.dotastats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class PlayerProfileActivity extends AppCompatActivity {
    Player player;

    private ServiceConnection maConnexion = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerProfileInterface myBinder = (PlayerProfileInterface) service;
            myBinder.setPlayer(player);

            ArrayList<View> views = new ArrayList<>();
            views.add(findViewById(R.id.namePlayer));
            views.add(findViewById(R.id.lastPlayed));
            views.add(findViewById(R.id.country));
            views.add(findViewById(R.id.mmr));
            views.add(findViewById(R.id.steamlink));
            views.add(findViewById(R.id.ranktier));
            views.add(findViewById(R.id.avatarProfile));
            myBinder.setViews(views);
        }
        public void onServiceDisconnected(ComponentName name) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        player = new Player();

        final Intent intentProfilePlayer = new Intent(getBaseContext(),PlayerProfileService.class);
        bindService(intentProfilePlayer, maConnexion, Context.BIND_AUTO_CREATE);
        startService(intentProfilePlayer);
    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = getIntent();
        player.setId(intent.getStringExtra("idPlayer"));
        player.setLastPlayed(intent.getStringExtra("lastPlayed"));
        player.setLastPlayed(player.getLastPlayed().substring(0,10));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(maConnexion);
        stopService(new Intent(this,PlayerProfileService.class));
    }
}
