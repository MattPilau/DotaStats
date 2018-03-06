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

import java.util.ArrayList;
import java.util.List;

// activity used when a user writes the name of a player => the app displays to him each player corresponding to what he wrote

/* TODO
    HTTP request to get all players with this name
    Display every player with avatar + name (+ possibly other information)
    link to each player page
 */
public class PlayersActivity extends AppCompatActivity {

    private Players players;
    private String namePlayer;

    private ServiceConnection maConnexion = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            SearchPlayerInterface myBinder = (SearchPlayerInterface) service;
            players = myBinder.getPlayers();
            myBinder.setName(namePlayer);
            myBinder.setListView((ListView) findViewById(R.id.list));
            myBinder.setProgressBar((ProgressBar) findViewById(R.id.progressBar));
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
