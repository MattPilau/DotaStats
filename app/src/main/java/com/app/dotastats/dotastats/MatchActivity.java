package com.app.dotastats.dotastats;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dotastats.dotastats.Interfaces.SearchMatchInterface;
import com.app.dotastats.dotastats.utils.UtilsPreferences;

import java.util.ArrayList;

public class MatchActivity extends AppCompatActivity {

    private MatchStat match;
    private PlayerStat player;
    private String idMatch;
    FragmentManager fragmentManager;
    //ArrayList<View> views = new ArrayList<>();


    private ServiceConnection maConnexion = new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder service){

            SearchMatchInterface myBinder = (SearchMatchInterface) service;
            myBinder.setListView1((ListView) findViewById(R.id.team1));
            myBinder.setListView2((ListView) findViewById(R.id.team2));

            myBinder.setIdMatch(idMatch);
            myBinder.setMatch(match);
            myBinder.setFragmentManager(fragmentManager);
        }

        public void onServiceDisconnected(ComponentName name) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        match = new MatchStat();

        final Intent intentMatch = new Intent(getBaseContext(), MatchService.class);
        bindService(intentMatch, maConnexion, Context.BIND_AUTO_CREATE);
        startService(intentMatch);

        fragmentManager = getFragmentManager();

    }

    @Override
    protected void onStart(){
        super.onStart();

        // get the name of the player
        Intent intent = getIntent();
        idMatch = intent.getStringExtra("idMatch");
        ((TextView)findViewById(R.id.idMatch)).setText(idMatch);
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
    }

}
