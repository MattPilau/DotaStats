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
import android.widget.TextView;

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
            myBinder.setImageView((ImageView) findViewById(R.id.avatar));
        }
        public void onServiceDisconnected(ComponentName name) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
    }

    @Override
    protected void onStart(){
        super.onStart();

        // get the name of the player
        Intent intent = getIntent();
        namePlayer = intent.getStringExtra("namePlayer");
        ((TextView)findViewById(R.id.namePlayer)).setText(namePlayer);

        final Intent intentSearchPlayer = new Intent(getBaseContext(),SearchPlayerService.class);
        bindService(intentSearchPlayer, maConnexion, Context.BIND_AUTO_CREATE);

        startService(intentSearchPlayer);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(maConnexion);
    }
}
