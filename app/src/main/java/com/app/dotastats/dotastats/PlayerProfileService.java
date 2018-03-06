package com.app.dotastats.dotastats;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.TimerTask;

public class PlayerProfileService extends Service {

    TaskProfile myTask;
    TimerTask task;
    Player player;

    private final IBinder binder = new MonServiceBinder();

    public PlayerProfileService() {
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
                        Toast.makeText(getBaseContext(), "Request made !" + player.getId(), Toast.LENGTH_SHORT).show();
                        myTask = new TaskProfile(player);
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

    private static class TaskProfile extends AsyncTask<Void, Void, Void> {
        Player player;

        TaskProfile(Player p){
            player = p;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void ...params) {

            String dataCleaned = new UtilsHttp().getInfoFromAPI("https://api.opendota.com/api/players/" + player.getId());

            try {
                JSONObject data = new JSONObject(dataCleaned);
                JSONObject profile = data.getJSONObject("profile");
                player.setCountry(profile.getString("loccountrycode"));
                Log.i("COUNTRY CODE",player.getCountry());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class MonServiceBinder extends Binder implements PlayerProfileInterface  {

        public void setPlayer(Player p){
            player = p;
        }
    }
}
