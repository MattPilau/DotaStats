package com.app.dotastats.dotastats.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import com.app.dotastats.dotastats.Interfaces.PlayerProfileInterface;
import com.app.dotastats.dotastats.LastHeroesFragment;
import com.app.dotastats.dotastats.LastMatchesFragment;
import com.app.dotastats.dotastats.Beans.Matches;
import com.app.dotastats.dotastats.Beans.MostPlayedHeroes;
import com.app.dotastats.dotastats.Beans.Player;
import com.app.dotastats.dotastats.PlayerProfileService;
import com.app.dotastats.dotastats.R;
import com.app.dotastats.dotastats.utils.UtilsPreferences;

public class PlayerProfileActivity extends AppCompatActivity {
    Player player;
    Boolean isFavorite;
    Boolean lastMatches = false;
    Matches matches;
    MostPlayedHeroes mostPlayedHeroes;
    FragmentManager fragmentManager;

    private ServiceConnection maConnexion = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerProfileInterface myBinder = (PlayerProfileInterface) service;
            myBinder.setPlayer(player);
            myBinder.setMatches(matches);
            myBinder.setMostPlayerHeroes(mostPlayedHeroes);

            ArrayList<View> views = new ArrayList<>();
            views.add(findViewById(R.id.namePlayer));
            views.add(findViewById(R.id.lastPlayed));
            views.add(findViewById(R.id.country));
            views.add(findViewById(R.id.mmr));
            views.add(findViewById(R.id.steamlink));
            views.add(findViewById(R.id.ranktier));
            views.add(findViewById(R.id.avatarProfile));
            myBinder.setViews(views);

            myBinder.setFragmentManager(fragmentManager);
        }
        public void onServiceDisconnected(ComponentName name) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        player = new Player();
        matches = new Matches();
        mostPlayedHeroes = new MostPlayedHeroes();

        final Intent intentProfilePlayer = new Intent(getBaseContext(),PlayerProfileService.class);
        bindService(intentProfilePlayer, maConnexion, Context.BIND_AUTO_CREATE);
        intentProfilePlayer.putExtra("matches",true);
        startService(intentProfilePlayer);

        fragmentManager = getFragmentManager();
    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = getIntent();
        if(intent.hasExtra("playerIndex")){
            player = UtilsPreferences.getSpecificPlayer(getBaseContext(),intent.getIntExtra("playerIndex",0));
        }
        else {
            player.setId(intent.getStringExtra("idPlayer"));
            player.setLastPlayed(intent.getStringExtra("lastPlayed"));
            player.setLastPlayed(player.getLastPlayed().substring(0, 10));
        }

        isFavorite = UtilsPreferences.isPlayerAFavorite(getApplicationContext(),player.getId());
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
                    UtilsPreferences.addNewPlayerToListFavorite(context,player);
                    Toast.makeText(getBaseContext(), "New Favorite Player !", Toast.LENGTH_SHORT).show();
                }
                else{
                    ((ImageButton) v).setImageResource(android.R.drawable.btn_star_big_off);
                    UtilsPreferences.removePlayerFromListFavorite(context,player);
                    Toast.makeText(getBaseContext(), "Player removed !", Toast.LENGTH_SHORT).show();
                }
                isFavorite = !isFavorite;
            }
        });

        findViewById(R.id.switch1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(lastMatches) {
                    goToLastMatchFragment();
                }else {
                    if(mostPlayedHeroes.getMostPlayedHeroes().size() == 0){
                        final Intent intentProfilePlayer = new Intent(getBaseContext(),PlayerProfileService.class);
                        bindService(intentProfilePlayer, maConnexion, Context.BIND_AUTO_CREATE);
                        intentProfilePlayer.putExtra("matches",false);
                        startService(intentProfilePlayer);

                        lastMatches = !lastMatches;
                    }
                    else
                        goToHeroesMostPlayedFragment();
                }

            }
        });


    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(maConnexion);
        stopService(new Intent(this,PlayerProfileService.class));
    }

    private void goToLastMatchFragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LastMatchesFragment lastMatchesFragment = new LastMatchesFragment();
        lastMatchesFragment.setMatches(matches);
        fragmentTransaction.replace(R.id.fragment_container, lastMatchesFragment);

        lastMatches = !lastMatches;
        fragmentTransaction.commit();
    }

    private void goToHeroesMostPlayedFragment(){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        LastHeroesFragment lastHeroesFragment = new LastHeroesFragment();
        lastHeroesFragment.setHeroes(mostPlayedHeroes);
        fragmentTransaction.replace(R.id.fragment_container,lastHeroesFragment);
        lastMatches = !lastMatches;
        fragmentTransaction.commit();
    }
}
