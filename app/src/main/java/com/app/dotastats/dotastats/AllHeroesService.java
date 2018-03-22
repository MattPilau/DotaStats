package com.app.dotastats.dotastats;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.app.dotastats.dotastats.Activity.HeroesActivity;
import com.app.dotastats.dotastats.Adapters.AllHeroAdapter;
import com.app.dotastats.dotastats.Beans.Hero;
import com.app.dotastats.dotastats.Beans.Heroes;
import com.app.dotastats.dotastats.Interfaces.AllHeroesInterface;
import com.app.dotastats.dotastats.utils.UtilsHttp;
import com.app.dotastats.dotastats.utils.UtilsPreferences;

public class AllHeroesService extends Service {

    private final IBinder binder = new MonServiceBinder();

    private TimerTask task;
    private MyTask myTask;
    private ProgressBar bar;
    private AllHeroAdapter adapter;
    private Heroes heroes;
    private Button button;

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


    private class MyTask extends AsyncTask<Void, Integer, Void> {

        private Heroes heroes;

        MyTask(Heroes data){
            heroes = data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
            bar.setMax(115);
            adapter.setHeroes(new ArrayList<Hero>());
            button.setEnabled(false);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            bar.setVisibility(View.GONE);
            adapter.setHeroes(heroes.getHeroes());
            button.setEnabled(true);
        }

        protected void onProgressUpdate(Integer ...values) {
            bar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void ...params) {

            ArrayList<String> filters = null;
            String dataCleaned = UtilsHttp.getInfoFromAPI("https://api.opendota.com/api/heroStats");

            try {
                JSONArray array = new JSONArray(dataCleaned);

                // if the user enabled the filters, the "all heroes search" will only display to him the heroes that matches the role he chose
                // if the used chose to display the "carrys", then all of them will be displayed but the other heroes will not be displayed
                if(UtilsPreferences.filtersEnabled(getApplicationContext())){
                    filters = UtilsPreferences.askedFilters(getApplicationContext());

                    for(int i = 0; i < array.length(); i++){
                        try {
                            JSONObject temp = array.getJSONObject(i);

                            JSONArray a = temp.getJSONArray("roles");
                            int j = 0;
                            Boolean isIn = false;
                            while(j < filters.size() && !isIn) {
                                if(a.toString().contains(filters.get(j))) {
                                    String name = temp.getString("localized_name");
                                    String url = temp.getString("img");
                                    Bitmap img = UtilsHttp.getHeroImage(url);

                                    heroes.addIntoList(name, img);
                                    publishProgress(new Integer(i));

                                    isIn = true;
                                }
                                j++;
                            }
                        }
                        catch (JSONException|IOException e){
                            e.printStackTrace();
                        }
                    }

                }
                // if he didn't enable the fiters, we just display every heroes of the game
                else{
                    for(int i = 0; i < array.length(); i++){
                        try {
                            JSONObject temp = array.getJSONObject(i);
                            String name = temp.getString("localized_name");
                            String url = temp.getString("img");
                            Bitmap img = UtilsHttp.getHeroImage(url);

                            heroes.addIntoList(name, img);
                            publishProgress(new Integer(i));
                        }
                        catch (JSONException|IOException e){
                            e.printStackTrace();
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class MonServiceBinder extends Binder implements AllHeroesInterface {
        public void setAdapter(AllHeroAdapter a) { adapter = a;}
        public void setBar(ProgressBar b){bar = b;}
        public void setHeroes(Heroes h){heroes = h;}
        public void setButton(Button b){button = b;}
    }
}
