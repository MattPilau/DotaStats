package com.app.dotastats.dotastats;

import android.app.Service;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;


/*TODO
 * Handle the results of the request in a listView taht displays the name, the picture and the last played game */

public class SearchPlayerService extends Service {

    private final IBinder binder = new MonServiceBinder();

    private TimerTask task;
    private MyTask myTask;
    private Players players = new Players();
    private String namePlayer;
    private ImageView i;

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
                        Toast.makeText(getBaseContext(), "plop !", Toast.LENGTH_SHORT).show();
                        myTask  = new MyTask(namePlayer,players,i);
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
        ImageView i;
        String name;

        MyTask(String namePlayer, Players data,ImageView img){
            players = data;
            i = img;
            name = namePlayer;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // temp display
            i.setImageBitmap(players.getPlayers().get(0).getAvatar());
        }

        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void ...params) {

            URL url;
            try {

                url = new URL("https://api.opendota.com/api/search?q="+name+"&similarity=1");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.connect();

                int status = connection.getResponseCode();
                if (status != HttpURLConnection.HTTP_OK)
                    Log.i("error",Integer.toString(status));

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();
                connection.disconnect();

                String dataCleaned = content.toString();
                Log.i("json",dataCleaned);

                try {
                    JSONArray data = new JSONArray(dataCleaned);

                    players.addAllValues(data);
                    players.display();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
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
        public void setImageView(ImageView img){
            i = img;
        }
    }
}
