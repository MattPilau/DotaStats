package com.app.dotastats.dotastats;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import comp.app.dotastats.utils.UtilsHttp;

public class SearchHeroService extends Service {


    private final IBinder binder = new MonServiceBinder();

    private TimerTask task;
    private MyTask myTask;
    private Hero hero;
    private String nameHero;
    ArrayList<View> views;

    public SearchHeroService() {
    }

    @Override
    public IBinder onBind(Intent intent){return binder;}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        final Handler handler = new Handler();

        task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(), "Request made !", Toast.LENGTH_SHORT).show();
                        myTask  = new MyTask(nameHero, hero);
                        myTask.execute();
                    } });
            }};
        task.run();
        return START_STICKY;
    }

    public void onDestroy() { // Destruction du service
        Toast.makeText(getBaseContext(), "DESTRUCTION", Toast.LENGTH_SHORT).show();
        task.cancel();
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        Hero hero;
        String name;

        MyTask(String nameHero, Hero data){
            hero = data;
            name = nameHero;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);



            ((TextView) views.get(0)).setText(Double.toString(hero.getBase_health())); // name
            ((TextView) views.get(1)).setText(Double.toString(hero.getBase_mana())); // last played
            ((TextView) views.get(2)).setText(Double.toString(hero.getAttack_range())); // country
            ((TextView) views.get(3)).setText(Double.toString(hero.getAttack_rate())); // mmr
            ((TextView) views.get(4)).setText(Double.toString(hero.getMove_speed())); // steamlink
            ((TextView) views.get(5)).setText(Double.toString(hero.getBase_armor()));
            ((TextView) views.get(6)).setText(Double.toString(hero.getBase_mr()));
            ((TextView) views.get(7)).setText(Double.toString(hero.getBase_str()));
            ((TextView) views.get(8)).setText(Double.toString(hero.getStr_gain()));
            ((TextView) views.get(9)).setText(Double.toString(hero.getBase_agi()));
            ((TextView) views.get(10)).setText(Double.toString(hero.getAgi_gain()));
            ((TextView) views.get(11)).setText(Double.toString(hero.getBase_int()));
            ((TextView) views.get(12)).setText(Double.toString(hero.getInt_gain()));// ranktier
            ((ImageView) views.get(13)).setImageBitmap(hero.getImage()); // avatar
        }

        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void ...params) {

            String dataCleaned = new UtilsHttp().getInfoFromAPI("https://api.opendota.com/api/heroStats");

            try {
                JSONArray data = new JSONArray(dataCleaned);

                hero.addHeroStats(data, name);
                hero.displayHero();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        //public Heroes getHeroes(){
            //return heroes;
        //}

    }

    private class MonServiceBinder extends Binder implements SearchHeroInterface  {

        public void setHeroName(String s) {nameHero = s;}
        public void setHero(Hero h){
            hero = h;
        }
        public void setViews(ArrayList<View> v){
            views = v;
        }
    }


}
