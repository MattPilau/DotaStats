package com.app.dotastats.dotastats.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.dotastats.dotastats.Beans.Hero;
import com.app.dotastats.dotastats.Interfaces.SearchHeroInterface;
import com.app.dotastats.dotastats.R;
import com.app.dotastats.dotastats.SearchHeroService;

import java.util.ArrayList;

// activity used when a user writes the name of a hero => the app displays to him each hero corresponding to what he wrote
public class HeroesActivity extends AppCompatActivity {

    private Hero hero;
    private String nameHero;
    private Boolean request;

    private ServiceConnection maConnexion = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            SearchHeroInterface myBinder = (SearchHeroInterface) service;
            myBinder.setHero(hero);
            myBinder.setHeroName(nameHero);

            ArrayList<View> views = new ArrayList<>();
            views.add(findViewById(R.id.BaseHealth));
            views.add(findViewById(R.id.BaseMana));
            views.add(findViewById(R.id.AttackRange));
            views.add(findViewById(R.id.AttackRate));
            views.add(findViewById(R.id.MoveSpeed));
            views.add(findViewById(R.id.BaseArmor));
            views.add(findViewById(R.id.BaseMR));
            views.add(findViewById(R.id.BaseStr));
            views.add(findViewById(R.id.StrGain));
            views.add(findViewById(R.id.BaseAgi));
            views.add(findViewById(R.id.AgiGain));
            views.add(findViewById(R.id.BaseInt));
            views.add(findViewById(R.id.IntGain));
            views.add(findViewById(R.id.HeroImage));
            myBinder.setViews(views);
        }
        public void onServiceDisconnected(ComponentName name) { }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroes);

        hero = new Hero();
        request= true;
    }

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
    protected void onStart(){
        super.onStart();

        if(request){
            final Intent intentSearchHero = new Intent(getBaseContext(),SearchHeroService.class);
            bindService(intentSearchHero, maConnexion, Context.BIND_AUTO_CREATE);
            startService(intentSearchHero);
        }

        Intent intent = getIntent();
        nameHero = intent.getStringExtra("nameHero");
        ((TextView)findViewById(R.id.HeroName)).setText(intent.getStringExtra("nameHero"));
    }

    @Override
    protected void onRestart(){
        super.onRestart();
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
        unbindService(maConnexion);
        stopService(new Intent(this,SearchHeroService.class));
        super.onDestroy();
    }

}
