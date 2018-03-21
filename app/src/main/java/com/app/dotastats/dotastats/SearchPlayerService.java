package com.app.dotastats.dotastats;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.app.dotastats.dotastats.Activity.PlayerProfileActivity;
import com.app.dotastats.dotastats.Adapters.PlayerAdapter;
import com.app.dotastats.dotastats.Beans.Player;
import com.app.dotastats.dotastats.Beans.Players;
import com.app.dotastats.dotastats.Interfaces.SearchPlayerInterface;
import com.app.dotastats.dotastats.utils.UtilsHttp;

public class SearchPlayerService extends Service {

    private final IBinder binder = new MonServiceBinder();

    private TimerTask task;
    private MyTask myTask;
    private Players players = new Players();
    private String namePlayer;
    private ProgressBar progressBar;
    private PlayerAdapter adapter;

    public SearchPlayerService() {
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
                        myTask  = new MyTask(namePlayer,players,adapter);
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

        Players players;
        String name;
        PlayerAdapter adapter;

        MyTask(String namePlayer, Players data, PlayerAdapter a){
            players = data;
            name = namePlayer;
            adapter = a;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            progressBar.setVisibility(View.GONE);

            ArrayList<Player> images = players.getPlayers();
            adapter.setPlayers(images);
        }

        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void ...params) {

            String dataCleaned = UtilsHttp.getInfoFromAPI("https://api.opendota.com/api/search?q=" + name + "&similarity=1");

            try {
                /* TODO
                bugs when wifi not launched
                => check if dataCleaned != null
                OR => change catch (Exception e)
                 */
                JSONArray data = new JSONArray(dataCleaned);

                players.addAllValues(data);
                players.display();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class MonServiceBinder extends Binder implements SearchPlayerInterface {

        public Players getPlayers(){
            return players;
        }
        public void setName(String s) {
            namePlayer = s;
        }
        public void setProgressBar(ProgressBar pBar){ progressBar = pBar; }
        public void setAdapter(PlayerAdapter a){ adapter = a ;}
    }
}
