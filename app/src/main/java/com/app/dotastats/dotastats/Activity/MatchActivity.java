package com.app.dotastats.dotastats.Activity;

import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.app.dotastats.dotastats.Beans.MatchStat;
import com.app.dotastats.dotastats.Beans.PlayerStat;
import com.app.dotastats.dotastats.Interfaces.SearchMatchInterface;
import com.app.dotastats.dotastats.MatchService;
import com.app.dotastats.dotastats.R;

public class MatchActivity extends AppCompatActivity {

    private MatchStat match;
    private PlayerStat player;
    private String idMatch;
    FragmentManager fragmentManager;

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

    // Action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:{
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;
            }
            case R.id.heart:{
                Intent intent = new Intent(this, FavoritePlayersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
        unbindService(maConnexion);
        stopService(new Intent(this,MatchService.class));
    }

}
