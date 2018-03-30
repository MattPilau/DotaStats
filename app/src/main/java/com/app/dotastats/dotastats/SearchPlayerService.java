package com.app.dotastats.dotastats;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
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

import com.app.dotastats.dotastats.Adapters.PlayerAdapter;
import com.app.dotastats.dotastats.Beans.Player;
import com.app.dotastats.dotastats.Beans.Players;
import com.app.dotastats.dotastats.Interfaces.SearchPlayerInterface;
import com.app.dotastats.dotastats.utils.UtilsHttp;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

// gets the list of players whose name matches what the user wrote
public class SearchPlayerService extends Service {

    private final IBinder binder = new MonServiceBinder();

    private TimerTask task;
    private MyTask myTask;
    private Players players = new Players();
    private String namePlayer;
    private ProgressBar progressBar;
    private PlayerAdapter adapter;
    private Button buttonWifi,buttonRefresh;

    public SearchPlayerService() {
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
                myTask  = new MyTask(namePlayer,players,adapter);
                myTask.execute();
            } });
        return START_STICKY;
    }

    public void onDestroy() { // Destruction du service
        myTask.cancel(true);
    }

    @SuppressLint("StaticFieldLeak")
    private class MyTask extends AsyncTask<Void, Void, Void> {

        Players players;
        String name;
        PlayerAdapter adapter;
        private Boolean internetError = false,playerError = false;

        MyTask(String namePlayer, Players data, PlayerAdapter a){
            players = data;
            name = namePlayer;
            adapter = a;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(buttonWifi != null)
               buttonWifi.setEnabled(false);
            if(buttonRefresh != null)
                buttonRefresh.setEnabled(false);
            if(progressBar != null )
                progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void result) {
            if(internetError){
                Toast.makeText(getBaseContext(), "No internet ! ", Toast.LENGTH_SHORT).show();
                buttonWifi.setEnabled(true);
            }
            else if(playerError)
                Toast.makeText(getBaseContext(),"There isn't any player named "+name+" !", Toast.LENGTH_SHORT).show();
            else {
                super.onPostExecute(result);
                ArrayList<Player> images = players.getPlayers();
                adapter.setPlayers(images);
            }
            progressBar.setVisibility(View.GONE);
        }

        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void ...params) {

            NetworkInfo info = ((ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if(info == null){
                internetError = true;
                return null;
            }

            String dataCleaned = UtilsHttp.getInfoFromAPI("https://api.opendota.com/api/search?q=" + name + "&similarity=1");

            try {

                if(dataCleaned.equals("[]") || dataCleaned==null){
                    playerError = true;
                    return null;
                }
                JSONArray data = new JSONArray(dataCleaned);

                players.addAllValues(data);
                players.display();

            } catch (JSONException | NullPointerException e) {
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
        public void setButton(Button b1,Button b2){buttonWifi = b1;buttonRefresh = b2;}
    }
}
