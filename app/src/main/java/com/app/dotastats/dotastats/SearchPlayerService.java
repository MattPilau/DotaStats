package com.app.dotastats.dotastats;

import android.app.Service;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.TimerTask;


/*TODO
 * Handle the results of the request in a listView that displays the name, the picture and the last played game */

public class SearchPlayerService extends Service {

    private final IBinder binder = new MonServiceBinder();

    private TimerTask task;
    private MyTask myTask;
    private Players players = new Players();
    private String namePlayer;
    private ListView listView;
    private ProgressBar progressBar;

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
                        myTask  = new MyTask(namePlayer,players);
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

        MyTask(String namePlayer, Players data){
            players = data;
            name = namePlayer;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            progressBar.setVisibility(View.GONE);

            List<Player> images = players.getPlayers();
            PlayerAdapter adapter = new PlayerAdapter(listView.getContext(), images);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
                {
                    Log.i("list",players.getPlayers().get(itemPosition).getName());

                    Intent myIntent = new Intent(listView.getContext(), PlayerProfileActivity.class);
                    String s = players.getPlayers().get(itemPosition).getId();
                    myIntent.putExtra("idPlayer", s);
                    listView.getContext().startActivity(myIntent);
                }
            });

        }

        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void ...params) {

            String dataCleaned = new UtilsHttp().getInfoFromAPI("https://api.opendota.com/api/search?q=" + name + "&similarity=1");

            try {
                JSONArray data = new JSONArray(dataCleaned);

                players.addAllValues(data);
                players.display();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Players getPlayers(){
            return players;
        }
    }

    private class MonServiceBinder extends Binder implements SearchPlayerInterface {

        public Players getPlayers(){
            return players;
        }
        public void setName(String s) {
            namePlayer = s;
        }
        public void setListView(ListView l){ listView = l;}
        public void setProgressBar(ProgressBar pBar){ progressBar = pBar; }
    }
}
