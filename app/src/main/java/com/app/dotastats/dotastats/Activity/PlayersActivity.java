package com.app.dotastats.dotastats.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dotastats.dotastats.Adapters.PlayerAdapter;
import com.app.dotastats.dotastats.Beans.Player;
import com.app.dotastats.dotastats.Interfaces.SearchPlayerInterface;
import com.app.dotastats.dotastats.R;
import com.app.dotastats.dotastats.SearchPlayerService;

import java.util.ArrayList;

// activity used when a user writes the name of a player => the app displays to him each player corresponding to what he wrote

public class PlayersActivity extends AppCompatActivity {

    private String namePlayer;
    private PlayerAdapter adapter;
    private Boolean request;

    private ServiceConnection maConnexion = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder service) {
            SearchPlayerInterface myBinder = (SearchPlayerInterface) service;
            myBinder.setName(namePlayer);
            myBinder.setProgressBar((ProgressBar) findViewById(R.id.progressBar));
            myBinder.setAdapter(adapter);
            myBinder.setButton((Button)findViewById(R.id.wifi),(Button)findViewById(R.id.refresh));
        }

        public void onServiceDisconnected(ComponentName name) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        request = true;

        // RecyclerView adapter needs to be created at this point, and will be updated once the data are received from the API
        adapter = new PlayerAdapter(new ArrayList<Player>());
        ((RecyclerView) findViewById(R.id.list)).setAdapter(adapter);
        ((RecyclerView) findViewById(R.id.list)).setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    protected void onStart(){
        super.onStart();

        if(request){
            final Intent intentSearchPlayer = new Intent(getBaseContext(),SearchPlayerService.class);
            bindService(intentSearchPlayer, maConnexion, Context.BIND_AUTO_CREATE);
            startService(intentSearchPlayer);
        }

        // get the name of the player
        Intent intent = getIntent();
        namePlayer = intent.getStringExtra("namePlayer");
        String s = getString(R.string.results) + " " + namePlayer;
        ((TextView)findViewById(R.id.namePlayer)).setText(s);

        findViewById(R.id.wifi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (wifiManager != null) {
                    wifiManager.setWifiEnabled(true);
                }

                Toast.makeText(getBaseContext(), "Waiting for Wifi ... ", Toast.LENGTH_SHORT).show();

                (findViewById(R.id.refresh)).setEnabled(true);
            }
        });

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(),SearchPlayerService.class));
                Intent newTrySearch = new Intent(getBaseContext(),SearchPlayerService.class);
                bindService(newTrySearch, maConnexion, Context.BIND_AUTO_CREATE);
                startService(newTrySearch);
            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        ((TextView)findViewById(R.id.namePlayer)).setText(R.string.results);
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
        stopService(new Intent(this,SearchPlayerService.class));
    }
}
