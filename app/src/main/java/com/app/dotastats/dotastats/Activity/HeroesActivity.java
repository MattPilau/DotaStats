package com.app.dotastats.dotastats.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import com.app.dotastats.dotastats.Beans.Hero;
import com.app.dotastats.dotastats.Interfaces.SearchHeroInterface;
import com.app.dotastats.dotastats.R;
import com.app.dotastats.dotastats.SearchHeroService;

// activity used when a user writes the name of a hero => the app displays to him each hero corresponding to what he wrote

public class HeroesActivity extends AppCompatActivity {

    private Hero hero;
    private String nameHero;


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

        final Intent intentSearchHero = new Intent(getBaseContext(),SearchHeroService.class);
        bindService(intentSearchHero, maConnexion, Context.BIND_AUTO_CREATE);
        startService(intentSearchHero);
    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = getIntent();
        nameHero = intent.getStringExtra("nameHero");
        ((TextView)findViewById(R.id.HeroName)).setText(intent.getStringExtra("nameHero"));
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
