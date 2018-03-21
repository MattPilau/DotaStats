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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.dotastats.dotastats.Adapters.PlayerAdapter;
import com.app.dotastats.dotastats.Beans.Player;
import com.app.dotastats.dotastats.Interfaces.SearchPlayerInterface;
import com.app.dotastats.dotastats.Beans.Players;
import com.app.dotastats.dotastats.R;
import com.app.dotastats.dotastats.SearchPlayerService;

import java.util.ArrayList;
import java.util.List;

// activity used when a user writes the name of a player => the app displays to him each player corresponding to what he wrote

public class PlayersActivity extends AppCompatActivity {

    private Players players;
    private String namePlayer;
    private PlayerAdapter adapter;

    private ServiceConnection maConnexion = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder service) {
            SearchPlayerInterface myBinder = (SearchPlayerInterface) service;
            players = myBinder.getPlayers();
            myBinder.setName(namePlayer);
            myBinder.setProgressBar((ProgressBar) findViewById(R.id.progressBar));
            myBinder.setAdapter(adapter);
        }

        public void onServiceDisconnected(ComponentName name) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        final Intent intentSearchPlayer = new Intent(getBaseContext(),SearchPlayerService.class);
        bindService(intentSearchPlayer, maConnexion, Context.BIND_AUTO_CREATE);
        startService(intentSearchPlayer);

        // RecyclerView adapter needs to be created at this point, and will be updated once the data are received from the API
        adapter = new PlayerAdapter(getBaseContext(),new ArrayList<Player>());
        ((RecyclerView) findViewById(R.id.list)).setAdapter(adapter);
        ((RecyclerView) findViewById(R.id.list)).setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    protected void onStart(){
        super.onStart();

        // get the name of the player
        Intent intent = getIntent();
        namePlayer = intent.getStringExtra("namePlayer");
        ((TextView)findViewById(R.id.namePlayer)).setText(((TextView)findViewById(R.id.namePlayer)).getText().toString() + namePlayer);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        ((TextView)findViewById(R.id.namePlayer)).setText("");
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
