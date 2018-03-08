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
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import comp.app.dotastats.utils.UtilsPreferences;

public class PlayerProfileActivity extends AppCompatActivity {
    Player player;
    Boolean isFavorite;

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

        isFavorite = new UtilsPreferences().isPlayerAFavorite(getApplicationContext(),player.getId());
        if(isFavorite)
            ((ImageButton)findViewById(R.id.addFavorite)).setImageResource(android.R.drawable.btn_star_big_on);

        // isFavorite is true when the player that we are checking is already registered
        // if he is, then you can unregister him; if he's not, you can register him
        findViewById(R.id.addFavorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();

                if(!isFavorite) {
                    ((ImageButton) v).setImageResource(android.R.drawable.btn_star_big_on);
                    new UtilsPreferences().addNewPlayerToListFavorite(context,player);
                    Toast.makeText(getBaseContext(), "New Favorite Player !", Toast.LENGTH_SHORT).show();
                }
                else{
                    ((ImageButton) v).setImageResource(android.R.drawable.btn_star_big_off);
                    new UtilsPreferences().removePlayerFromListFavorite(context,player);
                    Toast.makeText(getBaseContext(), "Player removed !", Toast.LENGTH_SHORT).show();
                }
                isFavorite = !isFavorite;

                // DEBUG
                ArrayList<Player> p = new UtilsPreferences().getAllFavoritePlayers(context);
                for(int i = 0; i < p.size(); i++){
                    Log.i(" NAME PLAYER " + (i+1)," "+p.get(i).getName());
                }
                if(p.size() == 0)
                    Log.i(" NAME PLAYER ","EMPTY EMPTY");
                // END DEBUG
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(maConnexion);
        stopService(new Intent(this,PlayerProfileService.class));
    }
}
