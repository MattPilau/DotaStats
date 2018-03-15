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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.TimerTask;

import com.app.dotastats.dotastats.utils.UtilsHttp;

public class AllHeroesService extends Service {

    private final IBinder binder = new MonServiceBinder();

    private TimerTask task;
    private MyTask myTask;
    private ListView listView;
    private Heroes heroes = new Heroes();


    public AllHeroesService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        final Handler handler = new Handler();

        // displays 'plop'
        task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(), "Request made !", Toast.LENGTH_SHORT).show();
                        myTask  = new MyTask(heroes);
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

        Heroes heroes;

        MyTask(Heroes data){
            heroes = data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            List<Hero> images = heroes.getHeroes();
            AllHeroAdapter adapter = new AllHeroAdapter(listView.getContext(), images);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
                {
                    Log.i("list",heroes.getHeroes().get(itemPosition).getName());

                    Intent myIntent = new Intent(listView.getContext(), HeroesActivity.class);

                    String s = heroes.getHeroes().get(itemPosition).getName();
                    myIntent.putExtra("nameHero", s);
                    listView.getContext().startActivity(myIntent);
                }
            });

        }

        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void ...params) {

            String dataCleaned = new UtilsHttp().getInfoFromAPI("https://api.opendota.com/api/heroStats");

            try {
                JSONArray data = new JSONArray(dataCleaned);

                heroes.addHeroStats(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public Heroes getHeroes(){
            return heroes;
        }
    }

























    private class MonServiceBinder extends Binder implements AllHeroesInterface {
        public void setListView(ListView l){ listView = l;}
    }
}
