package com.app.dotastats.dotastats;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.dotastats.dotastats.Adapters.AllHeroAdapter;
import com.app.dotastats.dotastats.Beans.Hero;
import com.app.dotastats.dotastats.Beans.Heroes;
import com.app.dotastats.dotastats.Interfaces.AllHeroesInterface;
import com.app.dotastats.dotastats.utils.UtilsHttp;
import com.app.dotastats.dotastats.utils.UtilsPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

// gets the list of all heroes depending on the filters decided by the user
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

        handler.post(new Runnable() {
            public void run() {
                myTask  = new MyTask(heroes);
                myTask.execute();
            } });
        return START_STICKY;
    }

    public void onDestroy() { // Destruction du service
        myTask.cancel(true);
    }

    @SuppressLint("StaticFieldLeak")
    private class MyTask extends AsyncTask<Void, Integer, Void> {

        private Heroes heroes;
        private Boolean internetError = false;

        MyTask(Heroes data){
            heroes = data;
        }

        // set visible the progress bar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
            bar.setMax(115);
            adapter.setHeroes(new ArrayList<Hero>());
            button.setEnabled(false);
        }

        // hide the progress bar and display the whole reycler view
        @Override
        protected void onPostExecute(Void result) {
            if(internetError)
                Toast.makeText(getBaseContext(), "No internet ! ", Toast.LENGTH_SHORT).show();
            else {
                super.onPostExecute(result);
                adapter.setHeroes(heroes.getHeroes());
            }
            bar.setVisibility(View.GONE);
            button.setEnabled(true);
        }

        protected void onProgressUpdate(Integer ...values) {
            bar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void ...params) {

            // if there isn't any internet, it cancels the request
            NetworkInfo info = ((ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if(info == null){
                internetError = true;
                return null;
            }

            ArrayList<String> filters;
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
                                    publishProgress(i);

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
                            publishProgress(i);
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
